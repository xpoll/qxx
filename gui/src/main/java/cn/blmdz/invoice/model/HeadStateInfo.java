package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 报文头信息-数据交换请求返回状态信息
 * 
 * 
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
public class HeadStateInfo {

	/**
	 * 返回代码
	 * 0000 成功，其他为错误
	 */
	@XStreamAlias("returnCode")
	private String returnCode;
	
	/**
	 * 返回描述
	 * 0000返回成功、其他返回错误描述，base64编码
	 */
	@XStreamAlias("returnMessage")
	private String returnMessage;
}
