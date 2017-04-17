package cn.blmdz.hunt.webc.resolver;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;

import cn.blmdz.common.exception.JsonResponseException;
import cn.blmdz.common.exception.NotFound404Exception;
import cn.blmdz.common.exception.ServiceException;
import cn.blmdz.common.exception.UnAuthorize401Exception;
import cn.blmdz.common.exception.UserNotLoginException;
import cn.blmdz.hunt.engine.MessageSources;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
public class ExceptionResolver extends ExceptionHandlerExceptionResolver {
	private String defaultErrorView;
	@Getter //TODO 去掉
	private Map<Integer, String> codeErrorViews;
	private static final String ERROR_DEFAULT = "失败了";
	private static final String ERROR_ARGS = "非法参数";
	private static final String ERROR_USER_NOT_LOGIN = "用户未登录";
	private static final String ERROR_USER_NOT_AUTH = "用户没权限";
	private static final String ERROR_PAGE_NOT_FOUND = "页面不存在";

	@Autowired
	private MessageSources messageSources;

	@PostConstruct
	public void init() {
		if (Strings.isNullOrEmpty(this.defaultErrorView))
			this.defaultErrorView = "classpath:pampas/views/error";
	}

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		if (handlerMethod == null) {
			return null;
		} else {
			Method method = handlerMethod.getMethod();
			if (method == null) {
				return null;
			} else {
				ExceptionResolver.Error error = this.buildError(exception);
				ResponseStatus responseStatus = (ResponseStatus) AnnotationUtils.findAnnotation(exception.getClass(),
						ResponseStatus.class);
				if (responseStatus != null) {
					error.setStatus(Integer.valueOf(responseStatus.value().value()));
				}

				ResponseBody responseBodyAnn = (ResponseBody) AnnotationUtils.findAnnotation(method,
						ResponseBody.class);
				if (!Objects.equal(request.getHeader("X-Requested-With"), "XMLHttpRequest")
						&& responseBodyAnn == null) {
					return new ModelAndView(this.defaultErrorView, ImmutableMap.of("error", error));
				} else {
					PrintWriter out = null;

					Object var11;
					try {
						response.setContentType(MediaType.JSON_UTF_8.toString());
						response.setStatus(error.getStatus().intValue());
						out = response.getWriter();
						out.print(error.getMsg());
						Object e = null;
						return (ModelAndView) e;
					} catch (Exception var15) {
						var11 = null;
					} finally {
						if (out != null) {
							out.close();
						}

					}

					return (ModelAndView) var11;
				}
			}
		}
	}

	private Error buildError(Exception exception) {
		if (exception instanceof JsonResponseException) {
			JsonResponseException jsonEx = (JsonResponseException) exception;
			Integer status = MoreObjects.firstNonNull(Integer.valueOf(jsonEx.getStatus()), Integer.valueOf(500));
			return new Error(status, MoreObjects.firstNonNull(this.messageSources.get(jsonEx.getMessage()), ERROR_DEFAULT));
		}
		if (exception instanceof ServiceException) {
			return new Error(Integer.valueOf(500), MoreObjects.firstNonNull(this.messageSources.get(exception.getMessage()), ERROR_DEFAULT));
		}
		if (exception instanceof NotFound404Exception) {
			return new Error(Integer.valueOf(404), MoreObjects.firstNonNull(this.messageSources.get(exception.getMessage()), ERROR_PAGE_NOT_FOUND));
		}
		if (exception instanceof UnAuthorize401Exception) {
			return new Error(Integer.valueOf(401), MoreObjects.firstNonNull(this.messageSources.get(exception.getMessage()), ERROR_USER_NOT_AUTH));
		}
		if (exception instanceof BindException) {
			BindException bindException = (BindException) exception;
			BindingResult result = bindException.getBindingResult();
			return new Error(Integer.valueOf(400), MoreObjects.firstNonNull(this.messageSources.get(result.getFieldError().getDefaultMessage()), ERROR_DEFAULT));
		}
		if (exception instanceof UserNotLoginException) {
			return new Error(Integer.valueOf(500), ERROR_USER_NOT_LOGIN);
		}
		if (exception instanceof IllegalArgumentException) {
			return new Error(Integer.valueOf(400), MoreObjects.firstNonNull(this.messageSources.get(exception.getMessage()), ERROR_ARGS));
		}
		if (exception.getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException cve = (ConstraintViolationException) exception.getCause();

			Set<ConstraintViolation<?>> violations = cve.getConstraintViolations();
			String firstError = this.messageSources.get(violations.iterator().next().getMessage());
			return new Error(Integer.valueOf(400), firstError);
		}
		return new Error(Integer.valueOf(500), MoreObjects.firstNonNull(this.messageSources.get(exception.getMessage()), ERROR_DEFAULT));
	}

	@Data
	@AllArgsConstructor
	private static class Error {
		private Integer status;
		private String msg;
	}
}