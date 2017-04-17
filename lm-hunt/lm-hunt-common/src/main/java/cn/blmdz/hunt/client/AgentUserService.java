package cn.blmdz.hunt.client;

import cn.blmdz.common.model.BaseUser;

public interface AgentUserService {
	BaseUser getUser(Long userId);
}