package cn.blmdz.invoice;

import java.util.ArrayList;
import java.util.List;

import cn.blmdz.invoice.enums.EnumInvoiceApi;
import cn.blmdz.invoice.model.OpenFpkjxxDdxx;
import cn.blmdz.invoice.model.OpenFpkjxxFptxx;
import cn.blmdz.invoice.model.OpenFpkjxxXmxx;
import cn.blmdz.invoice.model.OpenFpkjxxXmxxs;
import cn.blmdz.invoice.model.RequestOpenInvoice;
import cn.blmdz.invoice.util.ModelInvoice;

public class InvoiceOpen {

	public static void main(String[] args) throws Exception {
		RequestOpenInvoice model = new RequestOpenInvoice();

		OpenFpkjxxFptxx openFpkjxxFptxx = new OpenFpkjxxFptxx();	
		// 发票请求唯一流水号
		// 每张发票的请求唯一流水号无重复，由企业定义。
		// 前8位是企业的DSPTBM值。长度限制20~50位
		openFpkjxxFptxx.setFpqqlsh("dd2222aaadd22222222450");
		// 平台编码
		openFpkjxxFptxx.setDsptbm(ModelInvoice.DSPTBM);
		// 开票方识别号
		openFpkjxxFptxx.setNsrsbh(ModelInvoice.taxpayerId);
		// 开票方名称
		openFpkjxxFptxx.setNsrmc(ModelInvoice.empty);
		// 开票方电子档案号
		// F
		openFpkjxxFptxx.setNsrdzdah(ModelInvoice.empty);
		// 税务机构代码
		// F
		openFpkjxxFptxx.setSwjg_dm(ModelInvoice.empty);
		// 代开标志
		// 1、 自开(0)
		// 2、 代开(1)
		// 默认为自开
		openFpkjxxFptxx.setDkbz("0");
		// 票样代码
		// F
		openFpkjxxFptxx.setPydm(ModelInvoice.empty);
		// 主要开票项目
		// 主要开票商品，或者第一条商品，取项目信息中第一条数据的项目名称（或传递大类例如：办公用品）
		openFpkjxxFptxx.setKpxm("化妆品");
		// 编码表版本号，保持最新的版本号，目前12.0
		openFpkjxxFptxx.setBmb_bbh(ModelInvoice.version_bbh);
		// 销方识别号
		// 如果是企业自营开具发票，填写第3项中的开票方识别号，如果是商家驻店开具发票，填写商家的纳税人识别号
		openFpkjxxFptxx.setXhf_nsrsbh(ModelInvoice.taxpayerId);
		// 销方名称
		// 纳税人名称
		openFpkjxxFptxx.setXhfmc(ModelInvoice.empty);
		// 销方地址
		openFpkjxxFptxx.setXhf_dz("xxxxxx");
		// 销方电话
		openFpkjxxFptxx.setXhf_dh("213132");
		// 销方银行、账号
		// F
		openFpkjxxFptxx.setXhf_yhzh(ModelInvoice.empty);
		// 购货方名称，即发票抬头。
		openFpkjxxFptxx.setGhfmc("许嘉心");
		// 购货方识别号
		// 企业消费，如果填写识别号，需要传输过来
		// F
		openFpkjxxFptxx.setGhf_nsrsbh(ModelInvoice.empty);
		// 购货方地址
		// F
		openFpkjxxFptxx.setGhf_dz(ModelInvoice.empty);
		// 购货方省份
		// F
		openFpkjxxFptxx.setGhf_sf(ModelInvoice.empty);
		// 购货方固定电话
		// F
		openFpkjxxFptxx.setGhf_gddh(ModelInvoice.empty);
		// 购货方手机
		// F
		openFpkjxxFptxx.setGhf_sj(ModelInvoice.empty);
		// 购货方邮箱
		// F
		openFpkjxxFptxx.setGhf_email(ModelInvoice.empty);
		// 购货方企业类型
		// 01：企业
		// 02：机关事业单位
		// 03：个人
		// 04：其它
		openFpkjxxFptxx.setGhfqylx("01");
		// 购货方银行、账号
		// F
		openFpkjxxFptxx.setGhf_yhzh(ModelInvoice.empty);
		// 行业代码
		// F
		openFpkjxxFptxx.setHy_dm(ModelInvoice.empty);
		// 行业名称
		// F
		openFpkjxxFptxx.setHy_mc(ModelInvoice.empty);
		// 开票员
		openFpkjxxFptxx.setKpy("财务一");
		// 收款人
		// F
		openFpkjxxFptxx.setSky(ModelInvoice.empty);
		// 复核人
		// F
		openFpkjxxFptxx.setFhr(ModelInvoice.empty);
		// 格式YYY-MM-DD HH:MI:SS(由开票系统生成)
		// F
		openFpkjxxFptxx.setKprq(ModelInvoice.empty);
		// 开票类型
		// 1正票
		// 2红票
		openFpkjxxFptxx.setKplx("1");
		// 原发票代码
		// F
		// 如果CZDM不是10或KPLX为红票时候都是必录
		openFpkjxxFptxx.setYfp_dm("031001600311");
		// 原发票号码	
		// F
		// 如果CZDM不是10或KPLX为红票时候都是必录
		openFpkjxxFptxx.setYfp_hm("20060781");
		// 特殊冲红标志
		// F
		// 0正常冲红(电子发票) 1特殊冲红(冲红纸质等)
		openFpkjxxFptxx.setTschbz(ModelInvoice.empty);
		// 操作代码
		// 10正票正常开具 20退货折让红票
		openFpkjxxFptxx.setCzdm("10");
		// 清单标志
		// 默认为0。
		openFpkjxxFptxx.setQd_bz("0");
		// 清单发票项目名称
		// F
		// 清单标识（QD_BZ）为0不进行处理。
		openFpkjxxFptxx.setQdxmmc(ModelInvoice.empty);
		// 冲红原因
		// F
		// 冲红时填写，由企业定义
		openFpkjxxFptxx.setChyy(ModelInvoice.empty);
		// 价税合计金额
		// 小数点后2位，以元为单位精确到分
		openFpkjxxFptxx.setKphjje("20");
		// 合计不含税金额。所有商品行不含税金额之和。
		// 小数点后2位，以元为单位精确到分（单行商品金额之和）。平台处理价税分离，此值传0
		openFpkjxxFptxx.setHjbhsje("0");
		// 合计税额。所有商品行税额之和。
		// 小数点后2位，以元为单位精确到分（单行商品金额之和）。平台处理价税分离，此值传0
		openFpkjxxFptxx.setHjse("0");
		// 备注
		// F
		openFpkjxxFptxx.setBz(ModelInvoice.empty);
		// 备用字段
		// F
		openFpkjxxFptxx.setByzd1(ModelInvoice.empty);
		// 备用字段
		// F
		openFpkjxxFptxx.setByzd2(ModelInvoice.empty);
		// 备用字段
		// F
		openFpkjxxFptxx.setByzd3(ModelInvoice.empty);
		// 备用字段
		// F
		openFpkjxxFptxx.setByzd4(ModelInvoice.empty);
		// 备用字段
		// F
		openFpkjxxFptxx.setByzd5(ModelInvoice.empty);
		
		OpenFpkjxxXmxxs openFpkjxxXmxxs = new OpenFpkjxxXmxxs();
		
		List<OpenFpkjxxXmxx> openFpkjxxXmxxsList = new ArrayList<>();
		OpenFpkjxxXmxx openFpkjxxXmxx = new OpenFpkjxxXmxx();
		// 项目名称
		// 如FPHXZ=1，则此商品行为折扣行，此版本折扣行不允许多行折扣，折扣行必须紧邻被折扣行，项目名称必须与被折扣行一致。
		openFpkjxxXmxx.setXmmc("0J3M01 净痘凝胶 10ML ");
		// 项目单位
		// F
		openFpkjxxXmxx.setXmdw(ModelInvoice.empty);
		// 规格型号
		// F
		openFpkjxxXmxx.setGgxh(ModelInvoice.empty);
		// 项目数量
		// F
		// 小数点后8位, 小 数点后都是0时， PDF上只显示整数
		openFpkjxxXmxx.setXmsl("1");
		// 含税标志
		// 表示项目单价和项目金额是否含税。
		// 0标识都不含税，1标识都含税
		openFpkjxxXmxx.setHsbz("1");
		// 发票行性质
		// 0正常行 1折扣行 2被折扣行
		openFpkjxxXmxx.setFphxz("2");
		// 项目单价
		// 小数点后8位, 小 数点后都是0时， PDF上只显示2位小说；否则只显示至最后一位不为0的数字；（郑飘和红票单价都大于'0'）
		openFpkjxxXmxx.setXmdj("20");
		// 商品编码
		// 技术人员需向企业财务核实；不足19位后面补'0'
		openFpkjxxXmxx.setSpbm("1010101030000000000");
		// 字形编码
		// F
		openFpkjxxXmxx.setZxbm(ModelInvoice.empty);
		// 优惠政策标志
		// 0不适用，1使用
		openFpkjxxXmxx.setYhzcbs("0");
		// 零税率标志
		// F
		// 空：非零税率，1免税，2不征税，3普通零税率
		openFpkjxxXmxx.setLslbs(ModelInvoice.empty);
		// 增值税特殊管理
		// F
		// 当YHZCBS为1时必填(如：免税、不征税)
		openFpkjxxXmxx.setZzstsgl(ModelInvoice.empty);
		// 扣除额
		// F
		// 单位元，小数点2位小数 不能大于不含税金额 说明如下： 1. 差额征税的发票如果没有折扣的话，只能允许一条商品行。2. 具体差额征税发票的计算方法如下：不含税差额=不含税金额-扣除额；税额=额*税率。
		openFpkjxxXmxx.setKce("0");
		// 项目金额
		// 小数点后2位，以元为单位精确到分。 等于=单价*数量，根据含税标志，确定此金额是否为含税金额。
		openFpkjxxXmxx.setXmje("20");
		// 税率
		// 如果税率为 0，表示免税
		openFpkjxxXmxx.setSl("0.17");
		// 税额
		// F
		// 小数点后2位，以元为单位精确到分
		openFpkjxxXmxx.setSe(ModelInvoice.empty);
		// 备用字段
		openFpkjxxXmxx.setByzd1(ModelInvoice.empty);
		// 备用字段
		openFpkjxxXmxx.setByzd2(ModelInvoice.empty);
		// 备用字段
		openFpkjxxXmxx.setByzd3(ModelInvoice.empty);
		// 备用字段
		openFpkjxxXmxx.setByzd4(ModelInvoice.empty);
		// 备用字段
		openFpkjxxXmxx.setByzd5(ModelInvoice.empty);
		openFpkjxxXmxxsList.add(openFpkjxxXmxx);
		openFpkjxxXmxx = new OpenFpkjxxXmxx();
		openFpkjxxXmxx.setXmmc("0J3M01 净痘凝胶 10ML ");
		openFpkjxxXmxx.setXmdw(ModelInvoice.empty);
		openFpkjxxXmxx.setGgxh(ModelInvoice.empty);
		openFpkjxxXmxx.setXmsl("-1");
		openFpkjxxXmxx.setHsbz("1");
		openFpkjxxXmxx.setFphxz("1");
		openFpkjxxXmxx.setXmdj("10");
		openFpkjxxXmxx.setSpbm("1010101030000000000");
		openFpkjxxXmxx.setZxbm(ModelInvoice.empty);
		openFpkjxxXmxx.setYhzcbs("0");
		openFpkjxxXmxx.setLslbs(ModelInvoice.empty);
		openFpkjxxXmxx.setZzstsgl(ModelInvoice.empty);
		openFpkjxxXmxx.setKce("0");
		openFpkjxxXmxx.setXmje("-10");
		openFpkjxxXmxx.setSl("0.17");
		openFpkjxxXmxx.setSe(ModelInvoice.empty);
		openFpkjxxXmxx.setByzd1(ModelInvoice.empty);
		openFpkjxxXmxx.setByzd2(ModelInvoice.empty);
		openFpkjxxXmxx.setByzd3(ModelInvoice.empty);
		openFpkjxxXmxx.setByzd4(ModelInvoice.empty);
		openFpkjxxXmxx.setByzd5(ModelInvoice.empty);
		openFpkjxxXmxxsList.add(openFpkjxxXmxx);
		
		openFpkjxxXmxxs.setOpenFpkjxxXmxxs(openFpkjxxXmxxsList);
		openFpkjxxXmxxs.init();

		OpenFpkjxxDdxx openFpkjxxDdxx = new OpenFpkjxxDdxx();
		// 订单号
		openFpkjxxDdxx.setDdh("2492684718573093");

		// 退货单号
		// F
		// 在开具红字发票退货、折让的时候必须填写
		openFpkjxxDdxx.setThdh("2492684718573093");

		// 订单时间
		// F
		// 格式YYYY-MM-DD HH:MI:SS
		openFpkjxxDdxx.setDddate(ModelInvoice.empty);
		
		model.setOpenFpkjxxDdxx(openFpkjxxDdxx);
		model.setOpenFpkjxxFptxx(openFpkjxxFptxx);
		model.setOpenFpkjxxXmxxs(openFpkjxxXmxxs);
		
		ModelInvoice.postConversion(EnumInvoiceApi.INVOICE01, model, null);
	}
}
