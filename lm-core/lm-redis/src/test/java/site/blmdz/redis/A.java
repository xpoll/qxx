package site.blmdz.redis;

import java.util.Map;
import java.util.regex.Pattern;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Maps;

public class A {
	public static final Pattern email=Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	public static final Pattern name = Pattern.compile("^[\\d_a-zA-Z\\u4E00-\\u9FA5]{5,25}$");
	public static final Pattern mobile = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
	
	public static void main(String[] args) {
		String url = "http://mall.nbport.com.cn";
		String loginBy = "182245247522";
		String password = "1234561";
		String type = "0";
		//以上为页面传入
		if (mobile.matcher(loginBy).matches())
			type = "3";
		else if (email.matcher(loginBy).matches())
			type = "2";
		else if (name.matcher(loginBy).matches())
			type = "1";
		
		HttpRequest client = HttpRequest.post(url + "/api/user/login");
		Map<String, String> map = Maps.newHashMap();
		map.put("loginBy", loginBy);
		map.put("password", password);
		map.put("type", type);
		client.form(map);
		System.out.println(client.connectTimeout(10000).readTimeout(10000).body());
		/* 
		 * 返回结果:第一个为成功
		 * {"redirect":"/"}
		 * 用户不存在
		 * 密码不匹配
		 * 账号被冻结
		 */
	}
}
