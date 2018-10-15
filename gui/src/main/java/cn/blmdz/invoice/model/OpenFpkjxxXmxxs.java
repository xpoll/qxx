package cn.blmdz.invoice.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.Data;

/**
 * 项目信息（发票明细）（多条） 
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
@Data
public class OpenFpkjxxXmxxs {

	/**
	 * 项目信息（发票明细）
	 */
	@XStreamImplicit(itemFieldName="FPKJXX_XMXX")
	private List<OpenFpkjxxXmxx> openFpkjxxXmxxs;
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	@XStreamAsAttribute
	@XStreamAlias("size")
    private String b;
	
	public void init(){
		this.a = "FPKJXX_XMXX;";
		this.b = String.valueOf(this.openFpkjxxXmxxs.size());
	}
}
