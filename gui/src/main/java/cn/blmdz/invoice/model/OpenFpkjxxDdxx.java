package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

/**
 * 
 * 订单信息
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
@XStreamAlias("FPKJXX_DDXX")// 同步上一级 for初始化
public class OpenFpkjxxDdxx {
	
	/**
	 * 订单号
	 */
	@XStreamAlias("DDH")
	private String ddh;

	/**
	 * 退货单号
	 * F
	 * 在开具红字发票退货、折让的时候必须填写
	 */
	@XStreamAlias("THDH")
	private String thdh;

	/**
	 * 订单时间
	 * F
	 * 格式YYYY-MM-DD HH:MI:SS
	 */
	@XStreamAlias("DDDATE")
	private String dddate;
	
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	public OpenFpkjxxDdxx(){
		this.a = this.getClass().getAnnotation(XStreamAlias.class).value();
	}
}
