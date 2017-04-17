package cn.blmdz.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 好友
 * 
 * <pre>
 *  好友只能自己创建和更新
 *  所以没有创建人和更新人
 * </pre>
 * @author lm
 */
@Data
public class Friend implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id
	 */
	private Long id;
	/**
	 * 目标ID
	 */
	private Long fid;
	/**
	 * 好感度
	 */
	private Long favorability;
	/**
	 * 互粉
	 */
	private boolean powder;
	/**
	 * 赞
	 */
	private Long praise;
	/**
	 * 开关(默认关-未定义)
	 */
	private boolean switchs;

	/**
	 * <pre>
	 *  1: 可用
	 *  0: 未激活
	 * -1: 删除
	 * </pre>
	 */
	private Integer status;
	private Date cdate;
	private Date udate;
}
