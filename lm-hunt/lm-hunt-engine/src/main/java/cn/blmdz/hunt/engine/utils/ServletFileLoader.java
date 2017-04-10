package cn.blmdz.hunt.engine.utils;

import java.net.URL;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.io.Resources;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServletFileLoader implements FileLoader {

	@Autowired(required = false)
	private ServletContext servletContext;

	@Override
	public Resp load(String path) {
		if (this.servletContext == null)
			throw new IllegalStateException("no servlet context found");
		path = Protocol.removeProtocol(path, Protocol.SERVLET);
		FileLoader.Resp resp = new FileLoader.Resp();
		try {
			URL url = this.servletContext.getResource(path);
			resp.setContext(Resources.toByteArray(url));
			resp.setSign("UNSUPPORTED");
		} catch (Exception e) {
			log.error("error when load servlet file: {}", path, e);
			return FileLoader.Resp.NOT_FOUND;
		}
		return resp;
	}

	@Override
	public Resp load(String path, String sign) {
		return FileLoader.Resp.NOT_MODIFIED;
	}

}