package cn.blmdz.other;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

/**
 * request<br>
 * 
 * 5.发票信息推送API
 * 
 * @api ECXML.FPKJJG.TS.E_INV
 * 
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
@XStreamAlias("REQUEST_FPKJXX_FPJGXX_NEW")
public class RequestInvoiceInfoPush {
	
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
	 * F
	 */
	@XStreamAlias("KPLSH")
	private String kplsh;

	/**
	 * 防伪码
	 * F
	 * （RETURNCODE不为0000为空）
	 */
	@XStreamAlias("FWM")
	private String fwm;

	/**
	 * 二维码
	 * F
	 */
	@XStreamAlias("EWM")
	private String ewm;

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
	 * 格式 YYY-MM-DD HH:MI:SS
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
	 * Base64（PDF文件）
	 */
	@XStreamAlias("PDF_FILE")
	private String pdf_file;

	/**
	 * PDF下载路径
	 * （仅供对接企业内部使用）
	 */
	@XStreamAlias("PDF_URL")
	private String pdf_url;

	/**
	 * 操作代码
	 * F
	 * 10正品正常开具
	 * 20退货折让红票
	 */
	@XStreamAlias("CZDM")
	private String czdm;

	/**
	 * 结果代码
	 * 0000下发成功
	 * 9999下发失败
	 */
	@XStreamAlias("RETURNCODE")
	private String returncode;

	/**
	 * 结果描述
	 */
	@XStreamAlias("RETURNMESSAGE")
	private String returnmessage;
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	public void init(){
		this.a = this.getClass().getAnnotation(XStreamAlias.class).value();
	}
}
