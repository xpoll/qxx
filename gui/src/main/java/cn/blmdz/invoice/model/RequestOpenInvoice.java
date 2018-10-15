package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

/**
 * request<br>
 * 
 * 企业开通电子发票线上申请
 * 
 * @api ECXML.FPKJ.BC.E_INV
 * 
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
@XStreamAlias("REQUEST_FPKJXX")
public class RequestOpenInvoice {

	@XStreamAlias("FPKJXX_FPTXX")
	private OpenFpkjxxFptxx openFpkjxxFptxx;

	@XStreamAlias("FPKJXX_XMXXS")
	private OpenFpkjxxXmxxs openFpkjxxXmxxs;

	@XStreamAlias("FPKJXX_DDXX")
	private OpenFpkjxxDdxx openFpkjxxDdxx;
	
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	public RequestOpenInvoice(){
		this.a = this.getClass().getAnnotation(XStreamAlias.class).value();
	}
}
