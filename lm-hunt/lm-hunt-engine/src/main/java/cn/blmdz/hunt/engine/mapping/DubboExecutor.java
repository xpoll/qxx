package cn.blmdz.hunt.engine.mapping;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

import cn.blmdz.common.util.UserUtil;
import cn.blmdz.hunt.client.Agent;
import cn.blmdz.hunt.client.ParamUtil;
import cn.blmdz.hunt.client.WrapResp;
import cn.blmdz.hunt.engine.config.model.Service;
import cn.blmdz.hunt.engine.utils.DubboHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DubboExecutor extends Executor<Object> {
	private final DubboHelper dubboHelper;
	private LoadingCache<Service, LinkedHashMap<String, ParamInfo>> methodInfoCache;

	@Autowired
	public DubboExecutor(DubboHelper dubboHelper) {
		this.dubboHelper = dubboHelper;
		this.methodInfoCache = CacheBuilder.newBuilder()
				.build(new CacheLoader<Service, LinkedHashMap<String, ParamInfo>>() {
					@Override
					public LinkedHashMap<String, ParamInfo> load(Service service) throws Exception {
						Agent agent = getAgent(service.getApp());
						LinkedHashMap<String, cn.blmdz.hunt.client.Agent.ParamInfo> remoteParamInfos = agent
								.getParamsInfo(service.getUri());
						LinkedHashMap<String, ParamInfo> params = Maps.newLinkedHashMap();

						for (String name : remoteParamInfos.keySet()) {
							cn.blmdz.hunt.client.Agent.ParamInfo remoteParamInfo = (cn.blmdz.hunt.client.Agent.ParamInfo) remoteParamInfos
									.get(name);
							Class<?> paramClass = ParamUtil.getPrimitiveClass(remoteParamInfo.getClassName());
							if (paramClass == null) {
								try {
									paramClass = Class.forName(remoteParamInfo.getClassName());
								} catch (ClassNotFoundException var10) {
									paramClass = ParamConverter.UnKnowClass.class;
								}
							}

							params.put(name, new ParamInfo(remoteParamInfo.isOptional(), paramClass));
						}

						return params;
					}
				});
	}

	@Override
	public boolean detectType(Service service) {
		try {
			LinkedHashMap<String, ParamInfo> result = methodInfoCache.getUnchecked(service);
			return result != null;
		} catch (Exception var3) {
			log.warn(
					"detect dubbo service type for [{}] failed, it\'s maybe not an exception that need to attention. {}",
					service.getUri(), var3.getMessage());
			log.debug("detect dubbo service type for [{}] failed, debug info: {}", service,
					Throwables.getStackTraceAsString(var3));
			return false;
		}
	}

	@Override
	public Object exec(Service service, Map<String, Object> params) {
		LinkedHashMap<String, ParamInfo> methodParams = methodInfoCache.getUnchecked(service);
		Map<String, Object> args = Maps.newHashMap();
		boolean needContext = false;

		for (String paramName : methodParams.keySet()) {
			ParamInfo paramInfo = (ParamInfo) methodParams.get(paramName);
			Object arg = ParamConverter.convertParam(paramName, paramInfo.clazz, params, paramInfo.isOptional);
			if (arg != null) {
				args.put(paramName, arg);
			} else if (!ParamUtil.isBaseClass(paramInfo.clazz)) {
				needContext = true;
			}
		}

		Agent agent = this.getAgent(service.getApp());
		WrapResp wrapResp = agent.call(service.getUri(), args, needContext ? params : null);
		if (wrapResp.getCookie() != null) {
			UserUtil.getInnerCookie().merge(wrapResp.getCookie());
		}

		return this.unwrapResponse(wrapResp.getResult());
	}

	private Agent getAgent(String app) {
		return dubboHelper.getReference(Agent.class, app);
	}

	@AllArgsConstructor
	private static class ParamInfo {
		boolean isOptional;
		Class<?> clazz;
	}
}
