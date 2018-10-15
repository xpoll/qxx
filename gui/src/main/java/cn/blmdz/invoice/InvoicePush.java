package cn.blmdz.invoice;

import java.util.ArrayList;
import java.util.List;

import cn.blmdz.invoice.enums.EnumInvoiceApi;
import cn.blmdz.invoice.model.EmailCommonNode;
import cn.blmdz.invoice.model.EmailCommonNodes;
import cn.blmdz.invoice.model.EmailFpxx;
import cn.blmdz.invoice.model.EmailFpxxs;
import cn.blmdz.invoice.model.EmailTsfsxx;
import cn.blmdz.invoice.model.RequestEmailPush;
import cn.blmdz.invoice.model.ResponseEmailPush;
import cn.blmdz.invoice.util.ModelInvoice;

public class InvoicePush {

	public static void main(String[] args) throws Exception {
		RequestEmailPush model = new RequestEmailPush();
		
		EmailTsfsxx emailTsfsxx = new EmailTsfsxx();
		EmailCommonNodes emailCommonNodess1 = new EmailCommonNodes();
		List<EmailCommonNode> emailCommonNodes1 = new ArrayList<>();
		EmailCommonNode node = null;
		node = new EmailCommonNode();
		node.setName("TSFS");
		node.setValue("0");
		emailCommonNodes1.add(node);
		node = new EmailCommonNode();
		node.setName("EMAIL");
		node.setValue("1370200137@qq.com");
		emailCommonNodes1.add(node);
		emailCommonNodess1.setEmailCommonNodes(emailCommonNodes1);
		emailCommonNodess1.init();
		emailTsfsxx.setEmailCommonNodes(emailCommonNodess1);
		model.setEmailTsfsxx(emailTsfsxx);
		
		EmailFpxxs emailFpxxs2 = new EmailFpxxs();
		List<EmailFpxx> emailFpxxs = new ArrayList<>();
		EmailFpxx emailFpxx = new EmailFpxx();
		EmailCommonNodes emailCommonNodess2 = new EmailCommonNodes();
		List<EmailCommonNode> emailCommonNodes2 = new ArrayList<>();
		node = new EmailCommonNode();
		node.setName("FPQQLSH");
		node.setValue("dd2222aaadd22222222450");
		emailCommonNodes2.add(node);
		node = new EmailCommonNode();
		node.setName("NSRSBH");
		node.setValue(ModelInvoice.taxpayerId);
		emailCommonNodes2.add(node);
		node = new EmailCommonNode();
		node.setName("FP_DM");
		node.setValue("031001600311");
		emailCommonNodes2.add(node);
		node = new EmailCommonNode();
		node.setName("FP_HM");
		node.setValue("20718441");
		emailCommonNodes2.add(node);
		emailCommonNodess2.setEmailCommonNodes(emailCommonNodes2);
		emailCommonNodess2.init();
		emailFpxx.setEmailCommonNodes(emailCommonNodess2);
		emailFpxxs.add(emailFpxx);
		emailFpxxs2.setEmailFpxxs(emailFpxxs);
		emailFpxxs2.init();
		model.setEmailFpxxs(emailFpxxs2);
		
		ModelInvoice.postConversion(EnumInvoiceApi.INVOICE03, model, ResponseEmailPush.class);
	}
}
