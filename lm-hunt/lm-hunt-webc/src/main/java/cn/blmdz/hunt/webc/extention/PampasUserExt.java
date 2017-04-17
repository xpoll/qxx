package cn.blmdz.hunt.webc.extention;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.blmdz.common.model.BaseUser;
import cn.blmdz.hunt.client.AgentUserService;
import cn.blmdz.hunt.engine.utils.DubboHelper;

@Component
public class PampasUserExt implements UserExt {
	private static final Logger log = LoggerFactory.getLogger(PampasUserExt.class);
	@Autowired(required = false)
	private DubboHelper dubboHelper;

	@Override
	public BaseUser getUser(String appKey, Long userId) {
		if (dubboHelper == null) {
			log.error("no dubbo helper inject");
			throw new UnsupportedOperationException("no dubbo helper inject");
		} else {
			AgentUserService agentUserService = dubboHelper.getReference(AgentUserService.class, appKey);
			return agentUserService.getUser(userId);
		}
	}
}
