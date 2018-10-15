package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * 报文头信息-全局数据项
 * 
 * @author yongzongyang
 * @date 2017年9月17日
 */
@Data
public class HeadGlobalInfo {
	
	/**
	 * 终端类型标识代码
	 * 0:B/S请求来源 1:C/S请求来源
	 */
	@XStreamAlias("terminalCode")
	private String terminalCode;
	
	/**
	 * 应用标识
	 * 固定为：DZFP表示普通发票。ZZS_PT_DZFP表示增值税普通电子发票
	 */
	@XStreamAlias("appId")
	private String appId;
	
	/**
	 * API版本
	 * 当前1.0
	 */
	@XStreamAlias("version")
	private String version;
	
	/**
	 * API编码
	 * 不同API编码不一样，详见下面API列表
	 */
	@XStreamAlias("interfaceCode")
	private String interfaceCode;

	/**
	 * 平台编码
	 * 事先取得从管理端（请求前）
	 */
	@XStreamAlias("userName")
	private String userName;

	/**
	 * 密码
	 * 10位随机数+Base64({（10位随机数+注册码）MD5})。支持配置（配置时密码由平台提供，此时passWord空）
	 */
	@XStreamAlias("passWord")
	private String passWord;
	
	/**
	 * 数据交换请求发起方代码
	 * 用平台编码
	 */
	@XStreamAlias("requestCode")
	private String requestCode;
	
	/**
	 * 数据交换请求发出时间
	 * 格式YYYY-MM-DD HH:MI:SS
	 */
	@XStreamAlias("requestTime")
	private String requestTime;
	
	/**
	 * 纳税人识别号
	 * 事先取得从平台系统获取（请求前）
	 */
	@XStreamAlias("taxpayerId")
	private String taxpayerId;
	
	/**
	 * 纳税人授权码
	 * 事先取得从平台系统获取（请求前）
	 */
	@XStreamAlias("authorizationCode")
	private String authorizationCode;
	
	/**
	 * 数据交换请求接受方代码
	 */
	@XStreamAlias("responseCode")
	private String responseCode;
	
	/**
	 * 数据交换流水号（唯一
	 * requestCode+8位日期(YYYYMMDD)+9位序列号
	 */
	@XStreamAlias("dataExchangeId")
	private String dataExchangeId;
}
