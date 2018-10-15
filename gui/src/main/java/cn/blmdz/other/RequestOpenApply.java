package cn.blmdz.other;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

/**
 * request<br>
 * 
 * 企业开通申请API
 * 
 * @api ECXML.QYSQ.KTSQ
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
@Data
@XStreamAlias("REQUEST_QYKTSQ")
public class RequestOpenApply {
	
	/**
	 * 业务单号
	 * 企业申请唯一流水号
	 */
	@XStreamAlias("YWDH")
	private String ywdh;

	/**
	 * 纳税人名称
	 * 申请方纳税人名称
	 */
	@XStreamAlias("NSRMC")
	private String nsrmc;

	/**
	 * 纳税人识别号
	 * 申请方纳税人识别号
	 */
	@XStreamAlias("NSRSBH")
	private String nsrsbh;

	/**
	 * 纳税人地址
	 */
	@XStreamAlias("NSRDZ")
	private String nsrdz;

	/**
	 * 联系人
	 */
	@XStreamAlias("LXR")
	private String lxr;

	/**
	 * 联系电话
	 */
	@XStreamAlias("LXRDH")
	private String lxrdh;

	/**
	 * 备注
	 */
	@XStreamAlias("BZ")
	private String bz;
	
	
	// ---------------- @XStreamAsAttribute ---------------
	
	@XStreamAsAttribute
	@XStreamAlias("class")
    private String a;
	
	public void init(){
		this.a = this.getClass().getAnnotation(XStreamAlias.class).value();
	}
}
