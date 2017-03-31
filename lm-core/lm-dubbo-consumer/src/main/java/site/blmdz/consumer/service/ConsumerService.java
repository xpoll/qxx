package site.blmdz.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.blmdz.provider.service.ProviderService;

@Service
public class ConsumerService {

	@Autowired
	private ProviderService providerService;
}
