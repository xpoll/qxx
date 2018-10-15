package cn.blmdz.other;

import com.thoughtworks.xstream.annotations.XStreamAlias;

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
@XStreamAlias("RESPONSE_QYKTSQ")
public class ResponseOpenApply {
	
	/**
	 * 响应时间
	 * 申请表在几个工作日内处理，单位：工作日
	 */
	@XStreamAlias("XYSJ")
	private String xysj;

	/**
	 * 客服电话
	 * F
	 */
	@XStreamAlias("KFDH")
	private String kfdh;
}
