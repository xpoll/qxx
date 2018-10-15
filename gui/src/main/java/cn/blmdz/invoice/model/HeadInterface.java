package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

/**
 * 报文头信息-最外层
 * 
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
@XStreamAlias("interface")
public class HeadInterface<T> {

	@XStreamAlias("globalInfo")
	private HeadGlobalInfo globalInfo;
	
	@XStreamAlias("returnStateInfo")
	private HeadStateInfo returnStateInfo;
	
	@XStreamAlias("Data")
	private HeadData<T> data;

	
	// ---------------- @XStreamAsAttribute ---------------
	@XStreamAsAttribute
	@XStreamAlias("xmlns:xsi")
	private String a;
	
	@XStreamAsAttribute
	@XStreamAlias("xsi:schemaLocation")
	private String b;

	@XStreamAsAttribute
	@XStreamAlias("version")
	private String c;
	
	
	public void init() {
		this.a = "http://www.w3.org/2001/XMLSchema-instance";
		this.b = "http://www.chinatax.gov.cn/tirip/dataspec/interfaces.xsd";
		this.c = "DZFP1.0";
	}
}
