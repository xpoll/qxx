package cn.blmdz.invoice.enums;

/**
 * 航天发票API
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
public enum EnumInvoiceCompress {

	COMPRESS_0("0", "不压缩"),
	COMPRESS_1("1", "压缩"),
	;
	
	private String code;
	private String desc;
	
	public String code() {
		return this.code;
	}
	
	public String desc() {
		return this.desc;
	}
	
	EnumInvoiceCompress(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
