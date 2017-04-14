package cn.blmdz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.blmdz.exception.GlobalException;
import cn.blmdz.hunt.common.model.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常封装处理
 * @author yangyz
 */
@Slf4j
@ControllerAdvice
@Configuration
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
    public Response<?> exceptionHandler(
    		Exception e) throws Exception {
		if (e instanceof GlobalException) {
			log.debug("GlobalException:{}", e.getMessage());
			
			return Response.fail(e.getMessage());
		}
		log.debug("Exception:{}", e.getMessage());
		return Response.fail("系统异常");
    }
}
