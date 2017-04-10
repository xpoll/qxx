package cn.blmdz.hunt.engine.utils;

import java.io.File;

import org.springframework.stereotype.Component;

import com.google.common.io.Files;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LocalFileLoader implements FileLoader {

	@Override
	public Resp load(String path) {
		return load(path, null);
	}

	public Resp load(String path, String sign) {
		if (path.startsWith(Protocol.FILE.getPrefix()))
			path = Protocol.removeProtocol(path, Protocol.FILE);
		File file = new File(path);
		if (!file.exists()) {
			return Resp.NOT_FOUND;
		}
		if (sign != null && (file.lastModified() == Long.valueOf(sign).longValue())) {
			return Resp.NOT_MODIFIED;
		}
		Resp resp = new Resp();
		resp.setSign(String.valueOf(file.lastModified()));
		try {
			resp.setContext(Files.toByteArray(file));
		} catch (Exception e) {
			log.error("error when load local file: {}", path, e);
			return Resp.NOT_FOUND;
		}
		return resp;
	}

}