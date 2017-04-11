package cn.blmdz.boot.hunt.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.google.common.base.MoreObjects;

import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.design.DesignContext;
import cn.blmdz.hunt.engine.PageRender;
import cn.blmdz.hunt.engine.Setting;
import cn.blmdz.hunt.engine.SettingHelper;
import cn.blmdz.hunt.engine.debug.ProfiledAspect;
import cn.blmdz.hunt.engine.mapping.DubboExecutor;
import cn.blmdz.hunt.engine.utils.DubboHelper;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(PampasProperties.class)
@ComponentScan(
		basePackages = { "cn.blmdz.hunt.engine" },
		excludeFilters = {
				@Filter(Configuration.class),
				@Filter(type = FilterType.ASSIGNABLE_TYPE, value = { SettingHelper.class, PageRender.class })
				})
@Import(PampasMVCAutoConfiguration.class)
public class PampasAutoConfiguration {
	
	@Autowired
	private PampasProperties properties;
	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public Setting setting() {
		Setting setting = new Setting();
		setting.setRootPath(properties.getRootPath());
		setting.setApps(properties.getApps());
		setting.setRegistryId(properties.getRegistryId());
		setting.setDevMode(properties.isDevMode());
		setting.setLocale(properties.getLocale());
		setting.setClearInjectNestedContext(properties.isClearInjectNestedContext());
		return setting;
	}

	@Bean(name = { "pampasJedisTemplate" })
	@ConditionalOnProperty(prefix = "pampas.redis", name = { "jedisPool" })
	public JedisTemplate jedisTemplate1() {
		JedisPool jedisPool = applicationContext.getBean(properties.getRedis().getJedisPool(), JedisPool.class);
		return new JedisTemplate(jedisPool);
	}

	@Bean(name = { "pampasJedisTemplate" })
	@ConditionalOnMissingBean(name = { "pampasJedisTemplate" })
	@ConditionalOnProperty(prefix = "pampas.redis", name = { "host" })
	public JedisTemplate jedisTemplate2() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(20);
		jedisPoolConfig.setMaxIdle(2);
		jedisPoolConfig.setMaxWaitMillis(10000L);
		jedisPoolConfig.setTestOnBorrow(true);
		PampasProperties.RedisProperties redisProperties = properties.getRedis();
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost(),
				MoreObjects.firstNonNull(redisProperties.getPort(), Integer.valueOf(6379)).intValue(),
				MoreObjects.firstNonNull(redisProperties.getTimeout(), Integer.valueOf(2000)).intValue(),
				redisProperties.getPassword(),
				MoreObjects.firstNonNull(redisProperties.getDatabase(), Integer.valueOf(0)).intValue());
		return new JedisTemplate(jedisPool);
	}

	@Configuration
	@ConditionalOnProperty(prefix = "pampas", name = { "devMode" })
	@EnableAspectJAutoProxy
	public static class DebugLogAopConfiguration {
		@Bean
		public ProfiledAspect debugAspect() {
			return new ProfiledAspect();
		}
	}

	@Configuration
	@ConditionalOnProperty(prefix = "pampas", name = { "design.enable" })
	@ConditionalOnClass({ DesignContext.class })
	@Import({ DesignContext.class })
	public static class DesignEnableAutoConfiguration {
	}

	@Configuration
	@ConditionalOnProperty(prefix = "pampas", name = { "design.enable" }, havingValue = "false", matchIfMissing = true)
	public static class DesignNotEnableAutoConfiguration {
		@Bean
		public SettingHelper settingHelper() {
			return new SettingHelper();
		}

		@Bean
		public PageRender pageRender() {
			return new PageRender();
		}
	}

	@Configuration
	@ConditionalOnProperty(value = { "pampas.dubbo.enable" }, havingValue = "true")
	public static class DubboExecutorConfiguration {
		@Bean
		public DubboHelper dubboHelper(ApplicationContext applicationContext) {
			DubboHelper dubboHelper = new DubboHelper(applicationContext);
			dubboHelper.init();
			return dubboHelper;
		}

		@Bean
		public DubboExecutor dubboExecutor(DubboHelper dubboHelper) {
			return new DubboExecutor(dubboHelper);
		}
	}
}
