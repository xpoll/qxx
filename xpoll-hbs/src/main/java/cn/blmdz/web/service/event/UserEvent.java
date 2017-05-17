package cn.blmdz.web.service.event;

import java.io.Serializable;

import cn.blmdz.web.model.User;
import lombok.Getter;

public abstract class UserEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	private final User user;

	public UserEvent(User user) {
		this.user = user;
	}
}
