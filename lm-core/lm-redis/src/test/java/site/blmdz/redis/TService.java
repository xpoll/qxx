package site.blmdz.redis;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.terminus.common.utils.Splitters;

@Component
public class TService {
	
	@Value("${pampas.mvc.defaultErrorView}")
	private String error;


//	@Export({"request", "response"})
	public void test(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
//		request.getRequestDispatcher(Splitters.COLON.splitToList(error).get(1)).forward(request, response);
		response.sendRedirect(Splitters.COLON.splitToList(error).get(1));
	}
}