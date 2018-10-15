package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

@Data
@XStreamAlias("FPKJXX_FPTXX")// 同步上一级 for初始化
public class OpenFpkjxxFptxx {
	
	/**
	 * 发票请求唯一流水号
	 * 每张发票的请求唯一流水号无重复，由企业定义。
	 * 前8位是企业的DSPTBM值。长度限制20~50位
	 */
	@XStreamAlias("FPQQLSH")
	private String fpqqlsh;

	/**
	 * 平台编码
	 */
	@XStreamAlias("DSPTBM")
	private String dsptbm;

	/**
	 * 开票方识别号
	 */
	@XStreamAlias("NSRSBH")
	private String nsrsbh;

	/**
	 * 开票方名称
	 */
	@XStreamAlias("NSRMC")
	private String nsrmc;

	/**
	 * 开票方电子档案号
	 * F
	 */
	@XStreamAlias("NSRDZDAH")
	private String nsrdzdah;

	/**
	 * 税务机构代码
	 * F
	 */
	@XStreamAlias("SWJG_DM")
	private String swjg_dm;

	/**
	 * 代开标志
	 * 1、 自开(0)
	 * 2、 代开(1)
	 * 默认为自开
	 */
	@XStreamAlias("DKBZ")
	private String dkbz;

	/**
	 * 票样代码
	 * F
	 */
	@XStreamAlias("PYDM")
	private String pydm;

	/**
	 * 主要开票项目
	 * 主要开票商品，或者第一条商品，取项目信息中第一条数据的项目名称（或传递大类例如：办公用品）
	 */
	@XStreamAlias("KPXM")
	private String kpxm;

	/**
	 * 编码表版本号，保持最新的版本号，目前12.0
	 */
	@XStreamAlias("BMB_BBH")
	private String bmb_bbh;

	/**
	 * 销方识别号
	 * 如果是企业自营开具发票，填写第3项中的开票方识别号，如果是商家驻店开具发票，填写商家的纳税人识别号
	 */
	@XStreamAlias("XHF_NSRSBH")
	private String xhf_nsrsbh;

	/**
	 * 销方名称
	 * 纳税人名称
	 */
	@XStreamAlias("XHFMC")
	private String xhfmc;

	/**
	 * 销方地址
	 */
	@XStreamAlias("XHF_DZ")
	private String xhf_dz;

	/**
	 * 销方电话
	 */
	@XStreamAlias("XHF_DH")
	private String xhf_dh;

	/**
	 * 销方银行、账号
	 * F
	 */
	@XStreamAlias("XHF_YHZH")
	private String xhf_yhzh;

	/**
	 * 购货方名称，即发票抬头。
	 */
	@XStreamAlias("GHFMC")
	private String ghfmc;

	/**
	 * 购货方识别号
	 * 企业消费，如果填写识别号，需要传输过来
	 * F
	 */
	@XStreamAlias("GHF_NSRSBH")
	private String ghf_nsrsbh;

	/**
	 * 购货方地址
	 * F
	 */
	@XStreamAlias("GHF_DZ")
	private String ghf_dz;
	
	/**
	 * 购货方省份
	 * F
	 */
	@XStreamAlias("GHF_SF")
	private String ghf_sf;

	/**
	 * 购货方固定电话
	 * F
	 */
	@XStreamAlias("GHF_GDDH")
	private String ghf_gddh;

	/**
	 * 购货方手机
	 * F
	 */
	@XStreamAlias("GHF_SJ")
	private String ghf_sj;

	/**
	 * 购货方邮箱
	 * F
	 */
	@XStreamAlias("GHF_EMAIL")
	private String ghf_email;

	/**
	 * 购货方企业类型
	 * 01：企业
	 * 02：机关事业单位
	 * 03：个人
	 * 04：其它
	 */
	@XStreamAlias("GHFQYLX")
	private String ghfqylx;

	/**
	 * 购货方银行、账号
	 * F
	 */
	@XStreamAlias("GHF_YHZH")
	private String ghf_yhzh;

	/**
	 * 行业代码
	 * F
	 */
	@XStreamAlias("HY_DM")
	private String hy_dm;

	/**
	 * 行业名称
	 * F
	 */
	@XStreamAlias("HY_MC")
	private String hy_mc;

	/**
	 * 开票员
	 */
	@XStreamAlias("KPY")
	private String kpy;

	/**
	 * 收款人
	 * F
	 */
	@XStreamAlias("SKY")
	private String sky;

	/**
	 * 复核人
	 * F
	 */
	@XStreamAlias("FHR")
	private String fhr;

	/**
	 * 格式YYY-MM-DD HH:MI:SS(由开票系统生成)
	 * F
	 */
	@XStreamAlias("KPRQ")
	private String kprq;

	/**
	 * 开票类型
	 * 1正票
	 * 2红票
	 */
	@XStreamAlias("KPLX")
	private String kplx;

	/**
	 * 原发票代码
	 * F
	 * 如果CZDM不是10或KPLX为红票时候都是必录
	 */
	@XStreamAlias("YFP_DM")
	private String yfp_dm;

	/**
	 * 原发票号码	
	 * F
	 * 如果CZDM不是10或KPLX为红票时候都是必录
	 */
	@XStreamAlias("YFP_HM")
	private String yfp_hm;

	/**
	 * 特殊冲红标志
	 * F
	 * 0正常冲红(电子发票) 1特殊冲红(冲红纸质等)
	 */
	@XStreamAlias("TSCHBZ")
	private String tschbz;

	/**
	 * 操作代码
	 * 10正票正常开具 20退货折让红票
	 */
	@XStreamAlias("CZDM")
	private String czdm;

	/**
	 * 清单标志
	 * 默认为0。
	 */
	@XStreamAlias("QD_BZ")
	private String qd_bz;

	/**
	 * 清单发票项目名称
	 * F
	 * 清单标识（QD_BZ）为0不进行处理。
	 */
	@XStreamAlias("QDXMMC")
	private String qdxmmc;

	/**
	 * 冲红原因
	 * F
	 * 冲红时填写，由企业定义
	 */
	@XStreamAlias("CHYY")
	private String chyy;

	/**
	 * 价税合计金额
	 * 小数点后2位，以元为单位精确到分
	 */
	@XStreamAlias("KPHJJE")
	private String kphjje;

	/**
	 * 合计不含税金额。所有商品行不含税金额之和。
	 * 小数点后2位，以元为单位精确到分（单行商品金额之和）。平台处理价税分离，此值传0
	 */
	@XStreamAlias("HJBHSJE")
	private String hjbhsje;

	/**
	 * 合计税额。所有商品行税额之和。
	 * 小数点后2位，以元为单位精确到分（单行商品金额之和）。平台处理价税分离，此值传0
	 */
	@XStreamAlias("HJSE")
	private String hjse;

	/**
	 * 备注
	 * F
	 */
	@XStreamAlias("BZ")
	private String bz;

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

	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	public OpenFpkjxxFptxx(){
		this.a = this.getClass().getAnnotation(XStreamAlias.class).value();
	}
}
