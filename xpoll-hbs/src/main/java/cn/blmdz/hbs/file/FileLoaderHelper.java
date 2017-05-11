package cn.blmdz.hbs.file;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
public class FileLoaderHelper {
	@Autowired
	private FileLoaderHttp httpFileLoader;
	@Autowired
	private FileLoaderLocal localFileLoader;
	@Autowired
	private FileLoaderClasspath classpathFileLoader;
	@Autowired
	private FileLoaderServlet servletFileLoader;

	private Map<Protocol, FileLoader> loaderMap = Maps.newHashMap();

	@PostConstruct
	private void init() {
		loaderMap.put(Protocol.HTTP, httpFileLoader);
		loaderMap.put(Protocol.NONE, localFileLoader);
		loaderMap.put(Protocol.FILE, localFileLoader);
		loaderMap.put(Protocol.CLASSPATH, classpathFileLoader);
		loaderMap.put(Protocol.SERVLET, servletFileLoader);
	}

	public FileLoader.Resp load(String path) {
		Protocol protocol = Protocol.analyze(path);
		FileLoader fileLoader = loaderMap.get(protocol);
		if (fileLoader == null)
			throw new UnsupportedOperationException("unsupported protocol: " + protocol);
		else
			return fileLoader.load(path);
	}

	public FileLoader.Resp load(String path, String sign) {
		Protocol protocol = Protocol.analyze(path);
		FileLoader fileLoader = loaderMap.get(protocol);
		if (fileLoader == null)
			throw new UnsupportedOperationException("unsupported protocol: " + protocol);
		else
			return fileLoader.load(path, sign);
	}
}