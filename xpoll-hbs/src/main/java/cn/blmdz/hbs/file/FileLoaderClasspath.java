package cn.blmdz.hbs.file;

import java.net.URL;

import org.springframework.stereotype.Component;

import com.google.common.io.Resources;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileLoaderClasspath implements FileLoader {

	public Resp load(String path) {
		path = Protocol.removeProtocol(path, Protocol.CLASSPATH);
		try {
			URL e = Resources.getResource(path);
			Resp resp = new Resp();
			resp.setContext(Resources.toByteArray(e));
			resp.setSign("UNSUPPORTED");
			return resp;
		} catch (Exception e) {
			log.error("error when load classpath file: {}", path, e);
			return Resp.NOT_FOUND;
		}
	}

	public Resp load(String path, String sign) {
		return Resp.NOT_MODIFIED;
	}
}