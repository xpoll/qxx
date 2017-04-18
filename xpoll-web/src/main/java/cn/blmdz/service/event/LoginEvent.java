package cn.blmdz.service.event;

import cn.blmdz.model.User;

public class LoginEvent extends UserEvent {
	private static final long serialVersionUID = 1L;

	public LoginEvent(User user) {
		super(user);
	}
}
