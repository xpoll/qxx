package cn.blmdz.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 相册
 * 
 * <pre>
 *  只能自己创建和更新
 *  所以没有创建人和更新人
 *  删除则直接删除
 * </pre>
 * @author lm
 */
@Data
public class QxxAlbum implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id
	 */
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 标图
	 */
	private String img;
	
	/**
	 * 是否可见
	 */
	private Boolean see;
	/**
	 * 备注
	 */
	private String desc;

	/**
	 * @see cn.blmdz.enums.StatusType
	 */
	private Integer status;
	private Date cdate;
	private Date udate;
}
