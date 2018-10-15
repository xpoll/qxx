package cn.blmdz.invoice;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.xmlbeans.impl.util.Base64;

import cn.blmdz.invoice.enums.EnumInvoiceApi;
import cn.blmdz.invoice.enums.EnumInvoicePDF;
import cn.blmdz.invoice.model.HeadInterface;
import cn.blmdz.invoice.model.RequestDownInvoice;
import cn.blmdz.invoice.model.ResponseDownInvoice;
import cn.blmdz.invoice.util.ModelInvoice;

public class InvoiceDown {

	public static void main(String[] args) throws Exception {
		RequestDownInvoice model = new RequestDownInvoice();

		// 订单号
		model.setDdh("2492684718573093");
		// 发票请求唯一流水号
		model.setFpqqlsh("dd2222aaadd22222222450");
		// 平台编码
		model.setDsptbm(ModelInvoice.DSPTBM);
		// 开票方识别号
		model.setNsrsbh(ModelInvoice.taxpayerId);
		// PDF下载方式
		// 0 发票开具状态查询
		// 1 PDF文件(PDF_FILE)
		// 2 PDF链接(PDF_URL)
		model.setPdf_xzfs(EnumInvoicePDF.PDF01.code());
		
		HeadInterface<ResponseDownInvoice> resp = ModelInvoice.postConversion(EnumInvoiceApi.INVOICE02, model, ResponseDownInvoice.class);
		File file = new File("c://a.pdf");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(Base64.decode(resp.getData().getObj().getPdf_file().getBytes()));
		fos.close();
		
	}

}















