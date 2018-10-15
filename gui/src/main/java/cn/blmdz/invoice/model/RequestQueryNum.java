package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

/**
 * request<br>
 * 
 * 获取企业可用发票数量API
 * 
 * @api ECXML.QY.KPTTXX
 * 
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
@XStreamAlias("REQUEST_KYFPSL")
public class RequestQueryNum {

	/**
	 * 纳税人编号
	 */
	@XStreamAlias("NSRSBH")
	private String nsrsbh;
	
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	// ---------------- @AllArgsConstructor ---------------
	
	public RequestQueryNum(String nsrsbh) {
		this.nsrsbh = nsrsbh;
		this.a = this.getClass().getAnnotation(XStreamAlias.class).value();
	}

}
