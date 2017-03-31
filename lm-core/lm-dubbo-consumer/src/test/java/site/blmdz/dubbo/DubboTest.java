package site.blmdz.dubbo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import site.blmdz.provider.service.ProviderService;

public class DubboTest extends DubboBaseTest{


	@Autowired
	private ProviderService providerService;
	
	@Test
	public void get(){
		System.out.println(providerService.mothod("consumer"));
	}

}
