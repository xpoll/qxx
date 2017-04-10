package cn.blmdz.hunt.engine.utils;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

public class DubboHelper {
	private static final Logger log = LoggerFactory.getLogger(DubboHelper.class);
	private static final String DEFAULT_VERSION = "DEFAULT";
	private final ApplicationContext applicationContext;
	private LoadingCache<ReferenceKey, Optional<ReferenceObj>> referenceCache;
	private LoadingCache<ReferenceKey, ServiceBean<?>> providerCache;

	@Autowired
	public DubboHelper(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void init() {
		this.referenceCache = CacheBuilder.newBuilder().build(new CacheLoader<ReferenceKey, Optional<ReferenceObj>>() {
			@Override
			public Optional<ReferenceObj> load(ReferenceKey key) throws Exception {
				ReferenceBean<?> referenceBean = new ReferenceBean<>();
				referenceBean.setApplicationContext(applicationContext);
				referenceBean.setInterface(key.clazz);
				if (!Strings.isNullOrEmpty(key.version) && !Objects.equal(key.version, DEFAULT_VERSION)) {
					referenceBean.setVersion(key.version);
				}

				if (!Strings.isNullOrEmpty(key.url)) {
					referenceBean.setUrl(key.url);
				}

				try {
					referenceBean.afterPropertiesSet();
					return Optional.of(new ReferenceObj(referenceBean, referenceBean.get()));
				} catch (Exception var4) {
					log.error("error when init dubbo reference bean. class {}, version {}",
							new Object[] { key.clazz, key.version, var4 });
					return Optional.absent();
				}
			}
		});
		this.providerCache = CacheBuilder.newBuilder().build(new CacheLoader<ReferenceKey, ServiceBean<?>>() {
			@Override
			public ServiceBean<?> load(ReferenceKey key) throws Exception {
				ServiceBean<Object> serviceBean = new ServiceBean<>();
				serviceBean.setApplicationContext(applicationContext);
				serviceBean.setInterface(key.clazz);
				serviceBean.setRef(applicationContext.getBean(key.clazz));
				if (!Strings.isNullOrEmpty(key.version) && !Objects.equal(key.version, DEFAULT_VERSION)) {
					serviceBean.setVersion(key.version);
				}

				serviceBean.afterPropertiesSet();
				return serviceBean;
			}
		});
	}

	@PreDestroy
	private void destroy() {
		for (Optional<ReferenceObj> objOptional : this.referenceCache.asMap().values()) {
			if (objOptional.isPresent()) {
				((ReferenceObj) objOptional.get()).referenceBean.destroy();
			}
		}

		for (ServiceBean<?> serviceBean : this.providerCache.asMap().values()) {
			try {
				serviceBean.destroy();
			} catch (Exception var4) {
				log.warn("error when destroy dubbo serviceBean", var4);
			}
		}

	}

	public <T> T getReference(Class<T> clazz, String version) {
		return getReference(new ReferenceKey(clazz, version));
	}

	public <T> T getReference(Class<T> clazz, String version, String ip, Integer port) {
		port = MoreObjects.firstNonNull(port, Integer.valueOf(20880));
		String url = "dubbo://" + ip + ":" + port + "/" + clazz.getName();
		ReferenceKey key = new ReferenceKey(clazz, version);
		key.url = url;
		return getReference(key);
	}

	public <T> void exportProvider(Class<T> clazz, String version) {
		providerCache.getUnchecked(new ReferenceKey(clazz, version));
	}

	@SuppressWarnings("unchecked")
	private <T> T getReference(ReferenceKey key) {
		Optional<ReferenceObj> referenceOptional = referenceCache.getUnchecked(key);
		if (referenceOptional.isPresent())
			return (T) referenceOptional.get();
		return null;
	}

	@EqualsAndHashCode
	private static class ReferenceKey {
		private Class<?> clazz;
		private String version = DEFAULT_VERSION;
		private String url;

		private ReferenceKey(Class<?> clazz, String version) {
			this.clazz = clazz;
			this.version = version;
		}
	}

	@AllArgsConstructor
	private static class ReferenceObj {
		private ReferenceBean<?> referenceBean;
		@SuppressWarnings("unused")
		private Object bean;
		
	}
}
