package cn.blmdz.invoice;

import cn.blmdz.invoice.enums.EnumInvoiceApi;
import cn.blmdz.invoice.model.RequestQueryNum;
import cn.blmdz.invoice.model.ResponseQueryNum;
import cn.blmdz.invoice.util.ModelInvoice;

public class InvoiceQueryNum {

	public static void main(String[] args) throws Exception {
		
		RequestQueryNum model = new RequestQueryNum(ModelInvoice.taxpayerId);
		
		ModelInvoice.postConversion(EnumInvoiceApi.INVOICE10, model, ResponseQueryNum.class);
	}
}
