package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 项目信息（发票明细）（多条）
 * @author yongzongyang
 * @date 2017年9月18日
 */
@Data
public class DownInvoiceDetailFpmxxzXmxx {
	
	/**
	 * 项目名称
	 */
	@XStreamAlias("XMMC")
	private String xmmc;

	/**
	 * 项目单位
	 * F
	 */
	@XStreamAlias("XMDW")
	private String xmdw;

	/**
	 * 规格型号
	 * F
	 */
	@XStreamAlias("GGXH")
	private String ggxh;

	/**
	 * 项目数量
	 * F
	 */
	@XStreamAlias("XMSL")
	private String xmsl;

	/**
	 * 项目单价（不含税）
	 */
	@XStreamAlias("XMDJ")
	private String xmdj;

	/**
	 * 项目金额（不含税）
	 */
	@XStreamAlias("XMJE")
	private String xmje;

	/**
	 * 税率
	 */
	@XStreamAlias("SL")
	private String sl;

	/**
	 * 税额
	 */
	@XStreamAlias("SE")
	private String se;

	/**
	 * 税务编码
	 */
	@XStreamAlias("SWBM")
	private String swbm;

	/**
	 * 备用字段
	 * F
	 */
	@XStreamAlias("BYZD1")
	private String byzd1;

	/**
	 * 备用字段
	 * F
	 */
	@XStreamAlias("BYZD2")
	private String byzd2;

	/**
	 * 备用字段
	 * F
	 */
	@XStreamAlias("BYZD3")
	private String byzd3;

	/**
	 * 备用字段
	 * F
	 */
	@XStreamAlias("BYZD4")
	private String byzd4;

	/**
	 * 备用字段
	 * F
	 */
	@XStreamAlias("BYZD5")
	private String byzd5;

	/**
	 * 发票行性质
	 */
	@XStreamAlias("FPHXZ")
	private String fphxz;
}
