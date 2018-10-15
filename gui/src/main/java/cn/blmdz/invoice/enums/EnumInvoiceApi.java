package cn.blmdz.invoice.enums;

/**
 * 航天发票API
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
public enum EnumInvoiceApi {

	INVOICE01("ECXML.FPKJ.BC.E_INV", "发票开具"),
	INVOICE02("ECXML.FPXZ.CX.E_INV", "发票信息下载"),
	INVOICE03("ECXML.EMAILPHONEFPTS.TS.E.INV", "邮箱发票推送"),
	INVOICE04("ECXML.FPMXXZ.CX.E_INV", "发票明细信息下载"),
	INVOICE10("ECXML.QY.KYFPSL", "获取企业可用发票数量API"),
	;
	
	private String code;
	private String desc;
	
	public String code() {
		return this.code;
	}
	
	public String desc() {
		return this.desc;
	}
	
	EnumInvoiceApi(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
