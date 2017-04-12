package cn.blmdz.mail;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @author yongzongyang
 *
 * @pom.xml
 * 	groupId:	javax.mail <br>
 * 	artifactId:	mail <br>
 * 	version:	1.4.7 <br>
 */
public class TXEmail {

	public static void main(String[] args) throws Exception {
		email(Title.SYSTEM.desc(), "测试", "blmdz521@126.com");
	}
	
	public static void email (String title, String content, String sendTo) {
		EmailDto dto = new EmailDto();
		// 默认smtp方式
		dto.setHost("smtp.maxxipoint.com");
		dto.setUsername("yongzongyang@maxxipoint.com");
		dto.setPassword("xxxxxx");
		dto.setContent(content);
		dto.setSendTo(sendTo);
		dto.setSendName(SENDNAME);
		dto.setTitle(title);
		
		try {
			sendEmail(dto);
			System.out.println("发送成功");
		} catch (Exception e) {
			System.out.println("发送失败");
		}
	}

	public static void sendEmail(EmailDto dto) throws UnsupportedEncodingException, MessagingException {
		Properties props = new Properties();
		props.setProperty(dto.getProtocolName(), dto.getProtocol());
		props.setProperty(dto.getHostName(), dto.getHost());
		props.setProperty(dto.getAuthName(), dto.getAuth());

		Session session = Session.getDefaultInstance(props);
		session.setDebug(dto.getDebug());
		
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(dto.getUsername(), dto.getSendName(), dto.getCharset()));
		message.setRecipient(MimeMessage.RecipientType.TO,
				new InternetAddress(dto.getSendTo(), null, dto.getCharset()));
		message.setSubject(dto.getTitle(), dto.getCharset());
		message.setContent(dto.getContent(), dto.getContentType());
		message.setSentDate(new Date());
		message.saveChanges();
		
		Transport transport = session.getTransport();
		transport.connect(dto.getUsername(), dto.getPassword());
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
	
	private static String SENDNAME = "纳客宝";
	
	public static enum Title {
		SYSTEM("运维服务系统消息");
		
		private String desc;
		
		public String desc() {
			return this.desc;
		}
		
		Title (String desc) {
			this.desc = desc;
		}
	}

	@Getter
	@NoArgsConstructor
	public static class EmailDto {

		private String protocolName = "mail.transport.protocol";
		private String auth = "true";
		private String charset = "UTF-8";
		private String contentType = "text/html;charset=UTF-8";

		@Setter
		private String hostName = "mail.smtp.host";
		@Setter
		private String authName = "mail.smtp.auth";
		@Setter
		private String protocol = "smtp";
		@Setter
		private Boolean debug = Boolean.FALSE.booleanValue();
		
		@Setter
		private String host;
		@Setter
		private String username;
		@Setter
		private String password;
		@Setter
		private String title;
		@Setter
		private String content;
		@Setter
		private String sendTo;
		@Setter
		private String sendName;
		
		public EmailDto(String title, String content, String sendTo) {
			super();
			this.title = title;
			this.content = content;
			this.sendTo = sendTo;
		}
		
		
	}
}