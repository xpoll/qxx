package site.blmdz.base.session.service;

import javax.servlet.http.HttpServletRequest;

/**
 * SessionIdGenerator
 * SessionID 生成器
 * @author lm
 * @date 2016年10月31日 下午11:59:53
 */
public interface SessionIdGenerator {
	String generatorId(HttpServletRequest request);
}
