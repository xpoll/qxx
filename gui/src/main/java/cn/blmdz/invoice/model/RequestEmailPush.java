package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

/**
 * request<br>
 * 
 * 3.邮箱发票推送API
 * 
 * @api ECXMLEMAILPHONEFPTS.TS.E.INV
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
@Data
@XStreamAlias("REQUEST_EMAILPHONEFPTS")
public class RequestEmailPush {

	@XStreamAlias("TSFSXX")
	private EmailTsfsxx emailTsfsxx;

	@XStreamAlias("FPXXS")
	private EmailFpxxs emailFpxxs;
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	public RequestEmailPush(){
		this.a = this.getClass().getAnnotation(XStreamAlias.class).value();
	}

}
