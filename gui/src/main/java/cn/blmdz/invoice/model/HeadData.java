package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 报文头信息
 * 
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
public class HeadData<T> {

	/**
	 * 需要交换的数据内容
	 * 
	 * 加密流程： BASE64.encode(ZIP压缩(CA加密(xml明文)))
	 * 解密流程： CA解密(ZIP解压缩(BASE64.decode(xml密文)))
	 * 加解密过程中，是否解压缩与CA加解密，参照dataDescription中的zipCode与encryptCode
	 */
	@XStreamAlias("content")
	private String content;
	
	@XStreamAlias("dataDescription")
	private HeadDataDescription dataDescription;
	
	public T obj;
}
