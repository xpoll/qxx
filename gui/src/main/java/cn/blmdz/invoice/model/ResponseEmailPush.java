package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * response<br>
 * 
 * 邮箱发票推送API
 * 
 * @api ECXMLEMAILPHONEFPTS.TS.E.INV
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
@Data
@XStreamAlias("RESPONSE_EMAILPHONEFPTS")
public class ResponseEmailPush {
	
	public static final String replace = "class=\"COMMON_NODE;\"";

	@XStreamAlias("COMMON_NODES")
	private EmailCommonNodes emailCommonNodes;
}
