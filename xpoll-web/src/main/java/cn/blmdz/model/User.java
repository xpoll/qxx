package cn.blmdz.model;

import cn.blmdz.common.model.BaseUser;
import cn.blmdz.enums.AuthType;
import lombok.Data;

@Data
public class User implements BaseUser {
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private Long id;
	/**
	 * 父id
	 */
	private Long present;
	/**
	 * 昵称
	 */
	private String name;
	/**
	 * 角色
	 */
	private AuthType role;
	/**
	 * 账号类型
	 */
	private Integer type;
	/**
	 * 账号
	 */
	private String owner;
}
