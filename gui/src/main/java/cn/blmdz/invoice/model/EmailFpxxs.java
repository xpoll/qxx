package cn.blmdz.invoice.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.Data;

@Data
public class EmailFpxxs {
	
	@XStreamImplicit(itemFieldName="FPXX")
	private List<EmailFpxx> emailFpxxs;
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	@XStreamAsAttribute
	@XStreamAlias("size")
    private String b;
	
	public void init(){
		this.a = "FPXX;";
		this.b = String.valueOf(this.emailFpxxs.size());
	}
}
