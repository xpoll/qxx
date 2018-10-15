package cn.blmdz.invoice.enums;

/**
 * 航天发票API
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
public enum EnumInvoiceEncryptWay {

	WAY_0("0", "0"),
	WAY_1("3DES加密", "3DES加密"),
	WAY_2("CA加密", "CA加密"),
	;
	
	private String code;
	private String desc;
	
	public String code() {
		return this.code;
	}
	
	public String desc() {
		return this.desc;
	}
	
	EnumInvoiceEncryptWay(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
