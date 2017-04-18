package cn.blmdz.service.event;

import cn.blmdz.model.User;

public class LoginoutEvent extends UserEvent {
	private static final long serialVersionUID = 1L;

	public LoginoutEvent(User user) {
		super(user);
	}
}
