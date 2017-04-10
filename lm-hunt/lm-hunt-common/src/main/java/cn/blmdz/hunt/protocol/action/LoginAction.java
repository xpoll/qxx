package cn.blmdz.hunt.protocol.action;

import cn.blmdz.hunt.protocol.Action;
import lombok.Getter;
import lombok.Setter;

public class LoginAction extends Action {
	private static final long serialVersionUID = 6245111191588573507L;
	@Getter
	private Long userId;
	@Getter
	@Setter
	private Integer maxAge = Integer.valueOf(-1);

	public LoginAction(Long userId, Object data) {
		this.userId = userId;
		super.setData(data);
	}

}