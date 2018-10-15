package cn.blmdz.invoice.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import lombok.Data;

@Data
public class EmailCommonNodes {
	
	@XStreamImplicit(itemFieldName="COMMON_NODE")
	private List<EmailCommonNode> emailCommonNodes;
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	@XStreamAsAttribute
	@XStreamAlias("size")
    private String b;
	
	public void init(){
		this.a = "COMMON_NODE;";
		this.b = String.valueOf(this.emailCommonNodes.size());
	}
}
