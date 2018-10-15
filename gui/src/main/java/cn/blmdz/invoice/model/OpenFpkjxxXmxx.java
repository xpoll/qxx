package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 
 * 
 * 项目信息（发票明细）
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
@XStreamAlias("FPKJXX_XMXX")
public class OpenFpkjxxXmxx {
	
	/**
	 * 项目名称
	 * 如FPHXZ=1，则此商品行为折扣行，此版本折扣行不允许多行折扣，折扣行必须紧邻被折扣行，项目名称必须与被折扣行一致。
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
	 * 小数点后8位, 小 数点后都是0时， PDF上只显示整数
	 */
	@XStreamAlias("XMSL")
	private String xmsl;

	/**
	 * 含税标志
	 * 表示项目单价和项目金额是否含税。
	 * 0标识都不含税，1标识都含税
	 */
	@XStreamAlias("HSBZ")
	private String hsbz;

	/**
	 * 发票行性质
	 * 0正常行 1折扣行 2被折扣行
	 */
	@XStreamAlias("FPHXZ")
	private String fphxz;

	/**
	 * 项目单价
	 * 小数点后8位, 小 数点后都是0时， PDF上只显示2位小说；否则只显示至最后一位不为0的数字；（郑飘和红票单价都大于'0'）
	 */
	@XStreamAlias("XMDJ")
	private String xmdj;

	/**
	 * 商品编码
	 * 技术人员需向企业财务核实；不足19位后面补'0'
	 */
	@XStreamAlias("SPBM")
	private String spbm;

	/**
	 * 字形编码
	 * F
	 */
	@XStreamAlias("ZXBM")
	private String zxbm;

	/**
	 * 优惠政策标志
	 * 0不适用，1使用
	 */
	@XStreamAlias("YHZCBS")
	private String yhzcbs;

	/**
	 * 零税率标志
	 * F
	 * 空：非零税率，1免税，2不征税，3普通零税率
	 */
	@XStreamAlias("LSLBS")
	private String lslbs;

	/**
	 * 增值税特殊管理
	 * F
	 * 当YHZCBS为1时必填(如：免税、不征税)
	 */
	@XStreamAlias("ZZSTSGL")
	private String zzstsgl;

	/**
	 * 扣除额
	 * F
	 * 单位元，小数点2位小数 不能大于不含税金额 说明如下： 1. 差额征税的发票如果没有折扣的话，只能允许一条商品行。2. 具体差额征税发票的计算方法如下：不含税差额=不含税金额-扣除额；税额=额*税率。
	 */
	@XStreamAlias("KCE")
	private String kce;

	/**
	 * 项目金额
	 * 小数点后2位，以元为单位精确到分。 等于=单价*数量，根据含税标志，确定此金额是否为含税金额。
	 */
	@XStreamAlias("XMJE")
	private String xmje;

	/**
	 * 税率
	 * 如果税率为 0，表示免税
	 */
	@XStreamAlias("SL")
	private String sl;

	/**
	 * 税额
	 * F
	 * 小数点后2位，以元为单位精确到分
	 */
	@XStreamAlias("SE")
	private String se;

	/**
	 * 备用字段
	 */
	@XStreamAlias("BYZD1")
	private String byzd1;

	/**
	 * 备用字段
	 */
	@XStreamAlias("BYZD2")
	private String byzd2;

	/**
	 * 备用字段
	 */
	@XStreamAlias("BYZD3")
	private String byzd3;

	/**
	 * 备用字段
	 */
	@XStreamAlias("BYZD4")
	private String byzd4;

	/**
	 * 备用字段
	 */
	@XStreamAlias("BYZD5")
	private String byzd5;
}
