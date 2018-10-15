package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * request<br>
 * 
 * 获取企业可用发票数量API
 * 
 * @api ECXML.QY.KYFPSL
 * 
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
@XStreamAlias("RESPONSE_KYFPSL")
public class ResponseQueryNum {

	/**
	 * 纳税人编号
	 */
	@XStreamAlias("NSRSBH")
	private String nsrsbh;

	/**
	 * 可用发票数量
	 */
	@XStreamAlias("KYFPSL")
	private String kyfpsl;

	/**
	 * 统计时间
	 * 格式 yyyy-MM-dd HH:mi:ss
	 * 可用发票数量的统计时间
	 */
	@XStreamAlias("TJSJ")
	private String tjsj;
}
