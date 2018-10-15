package cn.blmdz.invoice.enums;

/**
 * 航天发票API
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
public enum EnumInvoiceEncryptFlag {

	FALG_0("0", "不加密"),
	FALG_1("1", "3DES"),
	FALG_2("2", "CA"),
	;
	
	private String code;
	private String desc;
	
	public String code() {
		return this.code;
	}
	
	public String desc() {
		return this.desc;
	}
	
	EnumInvoiceEncryptFlag(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
