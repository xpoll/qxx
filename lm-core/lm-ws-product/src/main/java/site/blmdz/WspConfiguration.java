package site.blmdz;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@ComponentScan(basePackages = {
		"site.blmdz.endpoint",
		"site.blmdz.service"
})
@Configuration
public class WspConfiguration extends WsConfigurerAdapter {
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}
	@Bean(name = "test")
	public DefaultWsdl11Definition testWsd(XsdSchema testSchema) {
		DefaultWsdl11Definition wsd = new DefaultWsdl11Definition();
		wsd.setPortTypeName("TestPort");
		wsd.setLocationUri("/ws");
		wsd.setTargetNamespace("http://blmdz.site/webs");
		wsd.setSchema(testSchema);
		return wsd;
	}

	@Bean
	public XsdSchema testSchema() {
		return new SimpleXsdSchema(new ClassPathResource("test.xsd"));
	}
}
