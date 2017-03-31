package site.blmdz.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import site.blmdz.service.TestService;
import site.blmdz.webs.GetTestRequest;
import site.blmdz.webs.GetTestResponse;

@Endpoint
public class TestEndPoint {
	private static final String NAMESPACE_URI = "http://blmdz.site/webs";
	private TestService testService;
	@Autowired
	public TestEndPoint(TestService testService){
		this.testService = testService;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTestRequest")
	@ResponsePayload
	public GetTestResponse test(@RequestPayload GetTestRequest request) {
		GetTestResponse response = new GetTestResponse();
		response.setName(testService.getName(request.getName()));
		return response;
	}
}
