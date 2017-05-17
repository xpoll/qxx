package cn.blmdz.web.service.event;

import cn.blmdz.web.model.User;

public class LoginEvent extends UserEvent {
	private static final long serialVersionUID = 1L;

	public LoginEvent(User user) {
		super(user);
	}
}
