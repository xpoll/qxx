package cn.blmdz.invoice.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 项目信息（发票明细）（多条）
 * @author yongzongyang
 * @date 2017年9月18日
 */
public class DownInvoiceDetailFpmxxzXmxxs {
	
	@XStreamImplicit(itemFieldName="FPMXXZ_XMXX")
	private List<DownInvoiceDetailFpmxxzXmxx> downInvoiceDetailFpmxxzXmxxs;
}
