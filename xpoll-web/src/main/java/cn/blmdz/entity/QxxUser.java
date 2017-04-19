package cn.blmdz.entity;

import java.io.Serializable;
import java.util.Date;

import cn.blmdz.enums.AuthType;
import lombok.Data;

/**
 * 用户
 * @author lm
 */
@Data
public class QxxUser implements Serializable {
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
	 * @see cn.blmdz.enums.AuthType
	 */
	private AuthType role;
	/**
	 * 账号类型
	 * @see cn.blmdz.enums.OwnerType
	 */
	private Integer type;
	/**
	 * 账号
	 */
	private String owner;
	/**
	 * 密码
	 */
	private String pwd;

	/**
	 * @see cn.blmdz.enums.StatusType
	 */
	private Integer status;
	private Long cid;
	private Long uid;
	private Date cdate;
	private Date udate;
}
