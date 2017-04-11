package cn.blmdz.mail;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class TEmail {
	public static String service = "http://www.blmdz.cn";
	public static String sender = "yongzongyang@maxxipoint.com";
	
	public static void main(String[] args) {
		String toAddress = "";
		String subject = "a";
		String content = "b";
		sendMail(toAddress, subject, content);
	}

	public static void sendMail(String toAddress, String subject, String content) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8"));
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("sender", sender);
		map.add("addressees", toAddress);
		map.add("subject", subject);
		map.add("type", "TEXT");
		map.add("content", content);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(service, requestEntity, String.class);
		System.out.println(result);
	}
}
