package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * <h1>邮箱发票推送</h1>
 * 
 * <pre>
 * <h2>推送方式信息</h2>
 * TSFS-M-推送方式-0邮箱推送
 * SJ-F-手机
 * EMAIL-M-邮箱
 * 扩展字段-扩展字段名称
 * </pre>
 * 
 * <pre>
 * <h2>发票信息</h2>
 * FPQQLSH-M-发票请求流水号
 * NSRSBH-M-开票方标识号
 * FP_DM-M-发票代码
 * FP_HM-M-发票号码
 * 扩展字段-扩展字段名称
 * </pre>
 * 
 * <pre>
 * <h2>返回信息</h2>
 * FPTSLSH-M-发票推送流水号-电子发票服务平台为每一个发票推送请求分配的一个唯一流水号
 * 扩展字段-扩展字段名称
 * </pre>
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
@Data
public class EmailCommonNode {

	@XStreamAlias("NAME")
	private String name;

	@XStreamAlias("VALUE")
	private String value;
}
