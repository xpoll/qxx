package site.blmdz.provider.service;

import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {

	@Override
	public String mothod (String name){
		
		System.out.println("enter ... name:" + name);
		
		return "enter ... name:" + name;
	}
}
