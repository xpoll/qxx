package cn.blmdz.invoice.enums;

/**
 * 航天发票API
 * 
 * @author yongzongyang
 * @date 2017年9月18日
 */
public enum EnumInvoicePDF {

	PDF00("0", "发票开具状态查询"),
	PDF01("1", "PDF文件"),
	PDF02("2", "PDF链接"),
	;
	private String code;
	private String desc;
	
	public String code() {
		return this.code;
	}
	
	public String desc() {
		return this.desc;
	}
	
	EnumInvoicePDF(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
