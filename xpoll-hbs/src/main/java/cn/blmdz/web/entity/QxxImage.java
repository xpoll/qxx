package cn.blmdz.web.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 图片
 * 
 * <pre>
 *  只能自己创建和更新
 *  所以没有创建人和更新人
 *  删除则直接删除
 * </pre>
 * @author lm
 */
@Data
public class QxxImage implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id
	 */
	private Long id;
	/**
	 * 相册Id
	 */
	private Long aid;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 大小
	 */
	private Integer size;
	/**
	 * 备注
	 */
	private String desc;
	/**
	 * 路径
	 */
	private String path;
	/**
	 * 其他信息
	 */
	private String extra;
	
	private Date cdate;
	private Date udate;
}
