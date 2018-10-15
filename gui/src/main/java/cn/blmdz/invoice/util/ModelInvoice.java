package cn.blmdz.invoice.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.xmlbeans.impl.util.Base64;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import cn.blmdz.invoice.enums.EnumInvoiceApi;
import cn.blmdz.invoice.enums.EnumInvoiceApp;
import cn.blmdz.invoice.enums.EnumInvoiceCompress;
import cn.blmdz.invoice.enums.EnumInvoiceEncryptFlag;
import cn.blmdz.invoice.enums.EnumInvoiceEncryptWay;
import cn.blmdz.invoice.model.HeadData;
import cn.blmdz.invoice.model.HeadDataDescription;
import cn.blmdz.invoice.model.HeadGlobalInfo;
import cn.blmdz.invoice.model.HeadInterface;
import cn.blmdz.invoice.model.HeadStateInfo;

public class ModelInvoice {
	// HTTP 请求地址
	public static final String url = "http://fw1test.shdzfp.com:9000/sajt-shdzfp-sl-http/SvrServlet";
	// 税号 - 识别号
	public static final String taxpayerId = "310101000000090";
	// 授权码
	public static final String authorizationCode = "3100000090";
	// 平台编码
	public static final String DSPTBM = "111MFWIK";
	//
	public static final String password = "6051435131bDo4Vs6uBMpJfjwVUdCiSwyy";
	// 注册码
	public static final String Registration_code = "92884519";
	// 版本号
	public static final String version = "1.0";
	// 编码表版本号，保持最新的版本号，目前12.0
	public static final String version_bbh = "12.0";

	public static final String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n";

	public static final String key_3des = "9oyKs7cVo1yYzkuisP9bhA==";

	public static final String empty = "";

	public static XStream xstream;

	public static CloseableHttpClient httpclient;

	static {
		xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
		xstream.processAnnotations(HeadInterface.class);
		xstream.autodetectAnnotations(true);
		httpclient = HttpClients.createDefault();
	}

	/**
	 * 公共头信息
	 * 
	 * @param api
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static <T> String head(EnumInvoiceApi api, T content) throws Exception {
		HeadInterface<T> head = new HeadInterface<T>();

		HeadGlobalInfo globalInfo = new HeadGlobalInfo();
		globalInfo.setTerminalCode(String.valueOf(0));
		globalInfo.setAppId(EnumInvoiceApp.ZZS_PT_DZFP.code());
		globalInfo.setVersion(version);
		globalInfo.setInterfaceCode(api.code());
		globalInfo.setUserName(DSPTBM);
		globalInfo.setPassWord(empty);
		globalInfo.setRequestCode(DSPTBM);
		globalInfo.setRequestTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		globalInfo.setTaxpayerId(taxpayerId);
		globalInfo.setAuthorizationCode(authorizationCode);
		globalInfo.setResponseCode("121");// TODO 接口方未解释要求固定
		globalInfo.setDataExchangeId(globalInfo.getRequestCode() + EnumInvoiceApp.ZZS_PT_DZFP.code()
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ String.format("%03d", (int) (Math.random() * 1000)));

		HeadData<T> data = new HeadData<T>();
		HeadDataDescription dataDescription = new HeadDataDescription();
		dataDescription.setZipCode(EnumInvoiceCompress.COMPRESS_0.code());
		dataDescription.setEncryptCode(EnumInvoiceEncryptFlag.FALG_1.code());
		dataDescription.setCodeType(EnumInvoiceEncryptWay.WAY_1.code());
		data.setDataDescription(dataDescription);
		System.out.println("----------------------内容体----------------------\n" + xstream.toXML(content));

		data.setContent(new String(
				Base64.encode(Test3DES.des3EncodeECB(key_3des.getBytes(), xstream.toXML(content).getBytes()))));
		HeadStateInfo stateInfo = new HeadStateInfo();
		stateInfo.setReturnCode(empty);
		stateInfo.setReturnMessage(empty);
		head.setGlobalInfo(globalInfo);
		head.setReturnStateInfo(stateInfo);
		head.setData(data);
		head.init();

		return xml + xstream.toXML(head);
	}

	/**
	 * 头内容解析
	 * 
	 * @param clazz
	 *            解析对象（replace需要固定去除需要些对应的参数）
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> HeadInterface<T> conversion(Class<?> clazz, String xml) throws Exception {
		if (xml == null || empty.equals(xml.trim()))
			return null;

		XStream xstream = new XStream() {
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@Override
					public boolean shouldSerializeMember(@SuppressWarnings("rawtypes") Class definedIn,
							String fieldName) {
						if (definedIn == Object.class) {
							return false;
						}
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
		xstream.processAnnotations(HeadInterface.class);
		HeadInterface<T> xxx = (HeadInterface<T>) xstream.fromXML(xml);
		if (xxx.getReturnStateInfo().getReturnMessage() != null) {
			xxx.getReturnStateInfo().setReturnMessage(
					new String(Base64.decode(xxx.getReturnStateInfo().getReturnMessage().getBytes())));
		}
		System.out.println(xxx.getReturnStateInfo().getReturnCode());
		System.out.println(xxx.getReturnStateInfo().getReturnMessage());
		if (clazz != null && xxx.getData().getContent() != null && !empty.equals(xxx.getData().getContent().trim())) {
			xstream.processAnnotations(clazz);
			byte[] bytes = Test3DES.ees3DecodeECB(key_3des.getBytes(),
					Base64.decode(xxx.getData().getContent().getBytes()));
			String resp = null;
			if (EnumInvoiceCompress.COMPRESS_1.code().equals(xxx.getData().getDataDescription().getZipCode())) {
				resp = new String(decompress(bytes));
			} else {
				resp = new String(bytes);
			}
			try {
				resp = resp.replace(clazz.getField("replace").get("replace").toString(), empty);
			} catch (Exception e) {
			}
			xxx.getData().setObj((T) xstream.fromXML(resp));
		}
		System.out.println(JSONObject.toJSONString(xxx.getData().getObj()));
		return xxx;
	}

	/**
	 * POST 请求
	 * 
	 * @param api
	 *            接口API
	 * @param content
	 *            请求体
	 * @return 返回内容
	 */
	public static <T> String post(EnumInvoiceApi api, T content) throws Exception {
		HttpPost httpPost = new HttpPost(ModelInvoice.url);
		httpPost.setHeader("Content-Type", "application/soap+xml; charset=utf-8");
		httpPost.setEntity(new StringEntity(ModelInvoice.head(api, content), "UTF-8"));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String resp = null;
		if (entity != null) {
			resp = EntityUtils.toString(entity);
		}
		System.err.println(
				"----------------------请求体----------------------\n" + EntityUtils.toString(httpPost.getEntity()));
		System.out.println("----------------------返回体----------------------\n" + resp);
		return resp;
	}

	public static <T, E> HeadInterface<E> postConversion(EnumInvoiceApi api, T content, Class<?> clazz)
			throws Exception {
		return conversion(clazz, post(api, content));
	}

	/**
	 * GZIP 解压缩
	 * 
	 * @throws IOException
	 */
	public static byte[] decompress(byte[] bytes) throws IOException {

		ByteArrayOutputStream bos = null;
		ByteArrayInputStream bis = null;
		GZIPInputStream gzis = null;
		try {
			bos = new ByteArrayOutputStream();
			bis = new ByteArrayInputStream(bytes);
			gzis = new GZIPInputStream(bis);

			byte[] bt = new byte[256];
			int i;
			while ((i = gzis.read(bt)) >= 0) {
				bos.write(bt, 0, i);
			}
			gzis.close();
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bis.close();
			bos.close();
			gzis.close();
		}
		return new byte[0];
	}

	/**
	 * GZIP 字符串压缩
	 * 
	 * @throws IOException
	 */
	public static byte[] compress(String data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gzos = new GZIPOutputStream(bos);
		try {
			gzos.write(data.getBytes());
			gzos.close();
			return bos.toByteArray();
		} finally {
			bos.close();
		}
	}
}
