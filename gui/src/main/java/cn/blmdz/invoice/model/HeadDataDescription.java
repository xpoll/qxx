package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 报文头信息-交换数据属性描述
 * 
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
public class HeadDataDescription {
	
	/**
	 * 压缩标志
	 * 
	 * 0 不压缩 1 压缩
	 * 数据包大于10k要求自动压缩.
	 * 平台返回时压缩标志为1时企业需要自行解压缩，为0时不需要解压缩。
	 */
	@XStreamAlias("zipCode")
	private String zipCode;
	
	/**
	 * 加密标志
	 * 0:不加密 1: 3DES加密 2: CA
	 */
	@XStreamAlias("encryptCode")
	private String encryptCode;
	
	/**
	 * 加密方式
	 * 0 、3DES加密 、CA加密
	 */
	@XStreamAlias("codeType")
	private String codeType;
}
