package cn.blmdz.hunt.engine.utils;

import org.springframework.stereotype.Component;

import com.github.kevinsawicki.http.HttpRequest;

@Component
public class HttpFileLoader implements FileLoader {

	@Override
	public Resp load(String path) {
		HttpRequest request = HttpRequest.get(path);
		if (request.notFound())
			return Resp.NOT_FOUND;
		Resp resp = new Resp();
		resp.setContext(request.bytes());
		resp.setSign(String.valueOf(request.lastModified()));
		return resp;
	}

	@Override
	public Resp load(String path, String sign) {
		HttpRequest request = HttpRequest.get(path);
		request.header("If-Modified-Since", sign);
		if (request.notFound())
			return Resp.NOT_FOUND;
		else if (request.notModified())
			return Resp.NOT_MODIFIED;
		Resp resp = new Resp();
		resp.setContext(request.bytes());
		resp.setSign(String.valueOf(request.lastModified()));
		return resp;
	}
}
