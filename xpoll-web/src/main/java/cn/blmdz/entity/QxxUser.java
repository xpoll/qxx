package cn.blmdz.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	/**
	 * 密码
	 */
	@JsonIgnore
	private String pwd;

	/**
	 * <pre>
	 *  1: 可用
	 *  0: 未激活
	 * -1: 删除
	 * </pre>
	 */
	private Integer status;
	private Long cid;
	private Long uid;
	private Date cdate;
	private Date udate;
}
