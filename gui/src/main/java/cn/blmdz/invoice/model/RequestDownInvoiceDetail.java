package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

/**
 * request<br>
 * 
 * 4.发票明细信息下载API
 * 
 * @api ECXML.FPMXXZ.CX.E_INV
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
@Data
@XStreamAlias("REQUEST_FPXXXZ_NEW")
public class RequestDownInvoiceDetail {

	/**
	 * 订单号
	 */
	@XStreamAlias("DDH")
	private String ddh;
	
	/**
	 * 发票请求唯一流水号
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
	 * PDF下载方式
	 * 0 发票开具状态查询
	 * 1 PDF文件(PDF_FILE)
	 * 2 PDF链接(PDF_URL)
	 */
	@XStreamAlias("PDF_XZFS")
	private String pdf_xzfs;
	
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	public RequestDownInvoiceDetail(){
		this.a = this.getClass().getAnnotation(XStreamAlias.class).value();
	}

}
