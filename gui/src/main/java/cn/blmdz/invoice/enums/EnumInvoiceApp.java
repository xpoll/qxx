package cn.blmdz.invoice.enums;

/**
 * 航天发票API
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
public enum EnumInvoiceApp {

	DZFP("DZFP", "普通发票"),
	ZZS_PT_DZFP("ZZS_PT_DZFP", "增值税普通电子发票"),
	;
	
	private String code;
	private String desc;
	
	public String code() {
		return this.code;
	}
	
	public String desc() {
		return this.desc;
	}
	
	EnumInvoiceApp(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
