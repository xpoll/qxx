package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * response<br>
 * 
 * 发票明细信息下载API
 * 
 * @api ECXML.FPMXXZ.CX.E_INV
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
@Data
@XStreamAlias("RESPONSE_FPMXXZ")
public class ResponseDownInvoiceDetail {
	
	public static final String replace = "class=\"FPMXXZ_XMXX;\"";

	/**
	 * 密码区域 
	 */
	@XStreamAlias("FPMW")
	private String fpmw;
	
	/**
	 * 校验码
	 */
	@XStreamAlias("JYM")
	private String jym;
	/**
	 * 机器编号
	 */
	@XStreamAlias("JQBH")
	private String jqbh;
	
	/**
	 * 发票请求唯一流水号
	 */
	@XStreamAlias("FPQQLSH")
	private String fpqqlsh;
	
	/**
	 * 订单号
	 */
	@XStreamAlias("DDH")
	private String ddh;
	
	/**
	 * 开票流水号
	 * 发票代码+发票号码（RETURNCODE不为0000为空）
	 */
	@XStreamAlias("KPLSH")
	private String kplsh;
	
	/**
	 * 发票种类代码
	 * （RETURNCODE不为0000为空）
	 */
	@XStreamAlias("FPZL_DM")
	private String fpzl_dm;
	
	/**
	 * 发票代码
	 * （RETURNCODE不为0000为空）
	 */
	@XStreamAlias("FP_DM")
	private String fp_dm;
	
	/**
	 * 发票号码
	 * （RETURNCODE不为0000为空）
	 */
	@XStreamAlias("FP_HM")
	private String fp_hm;
	
	/**
	 * 开票日期
	 * 格式YYY-MM-DD HH:MI:SS
	 * （RETURNCODE不为0000为空）
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
	 * 不含税金额
	 */
	@XStreamAlias("HJBHSJE")
	private String hjbhsje;
	
	/**
	 * 税额
	 */
	@XStreamAlias("KPHJSE")
	private String kphjse;
	
	/**
	 * Base64(PDF文件)
	 * F
	 * PDF下载方式（PDF_XZFS为1时必填）
	 */
	@XStreamAlias("PDF_FILE")
	private String pdf_file;
	
	/**
	 * PDF下载路径
	 * F
	 */
	@XStreamAlias("PDF_URL")
	private String pdf_url;
	
	/**
	 * 结果代码
	 * 0000下发成功
	 * 9999下发失败
	 */
	@XStreamAlias("RETURNCODE")
	private String returncode;
	
	/**
	 * 结果描述
	 * F
	 */
	@XStreamAlias("RETURNMESSAGE")
	private String returnmessage;
	
	/**
	 * 扣除额
	 * F
	 */
	@XStreamAlias("KCE")
	private String kce;
	
	/**
	 * 购货方名称
	 * 发票抬头
	 */
	@XStreamAlias("GHFMC")
	private String ghfmc;
	
	/**
	 * 购货方识别号
	 * F
	 * 企业消防，如果填写识别号，需要传输过来
	 */
	@XStreamAlias("GHF_NSRSBH")
	private String ghf_nsrsbh;
	
	/**
	 * 购货方地址、电话
	 * F
	 */
	@XStreamAlias("GHF_DZDH")
	private String ghf_dzdh;
	
	/**
	 * 购货方银行、账号
	 * F
	 */
	@XStreamAlias("GHF_YHZH")
	private String ghf_yhzh;
	
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
	 * 开票合计金额（价税合计）
	 */
	@XStreamAlias("KPHJJE")
	private String kphjje;
	
	/**
	 * 销方税号
	 */
	@XStreamAlias("XHF_NSRSBH")
	private String xhf_nsrsbh;
	
	/**
	 * 销方名称
	 */
	@XStreamAlias("XHFMC")
	private String xhfmc;
	
	/**
	 * 销方地址、电话
	 */
	@XStreamAlias("XHF_DZDH")
	private String xhf_dzdh;
	
	/**
	 * 销方银行、账号
	 * F
	 */
	@XStreamAlias("XHF_YHZH")
	private String xhf_yhzh;
	
	/**
	 * 开票员
	 */
	@XStreamAlias("KPY")
	private String kpy;
	
	/**
	 * 收款员
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
	 * 备注
	 * F
	 */
	@XStreamAlias("BZ")
	private String bz;
	
	/**
	 * 原发票代码
	 * F
	 */
	@XStreamAlias("YFP_DM")
	private String yfp_dm;
	
	/**
	 * 原发票号码
	 * F
	 */
	@XStreamAlias("YFP_HM")
	private String yfp_hm;

	@XStreamAlias("FPMXXZ_XMXXS")
	private DownInvoiceDetailFpmxxzXmxxs downInvoiceDetailFpmxxzXmxxs;
}
