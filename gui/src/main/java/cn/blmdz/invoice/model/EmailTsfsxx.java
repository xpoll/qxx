package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

@Data
@XStreamAlias("TSFSXX")// 同步上一级 for初始化
public class EmailTsfsxx {

	@XStreamAlias("COMMON_NODES")
	private EmailCommonNodes emailCommonNodes;
	
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	public EmailTsfsxx(){
		this.a = this.getClass().getAnnotation(XStreamAlias.class).value();
	}
}
