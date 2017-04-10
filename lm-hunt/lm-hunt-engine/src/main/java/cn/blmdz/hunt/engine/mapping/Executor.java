package cn.blmdz.hunt.engine.mapping;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.blmdz.hunt.common.exception.ServiceException;
import cn.blmdz.hunt.common.model.Response;
import cn.blmdz.hunt.engine.MessageSources;
import cn.blmdz.hunt.engine.config.model.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Executor<T> {
	@Autowired(required = false)
	protected MessageSources messageSources;

	/**
	 * 检测类型
	 */
	public abstract boolean detectType(Service service);

	/**
	 * 执行
	 */
	public abstract T exec(Service service, Map<String, Object> params);

	/**
	 * 回应
	 */
	protected Object unwrapResponse(T result) {
		if (result == null) {
			return null;
		} else if (result instanceof Response) {
			Response<?> resp = (Response<?>) result;
			if (resp.isSuccess()) {
				return resp.getResult();
			} else {
				log.error("failed to execute service, error code:{}", resp.getError());
				if (this.messageSources != null) {
					throw new ServiceException(this.messageSources.get(resp.getError()));
				} else {
					throw new ServiceException(resp.getError());
				}
			}
		} else {
			return result;
		}
	}
}
