package cn.blmdz.hunt.engine.utils;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import cn.blmdz.hunt.engine.utils.FileLoader.Resp;

@Component
public class FileLoaderHelper {
	@Autowired
	private HttpFileLoader httpFileLoader;
	@Autowired
	private LocalFileLoader localFileLoader;
	@Autowired
	private ClasspathFileLoader classpathFileLoader;
	@Autowired
	private ServletFileLoader servletFileLoader;
	@Autowired
	private RedisFileLoader redisFileLoader;

	private Map<Protocol, FileLoader> loaderMap = Maps.newHashMap();

	@PostConstruct
	private void init() {
		loaderMap.put(Protocol.HTTP, httpFileLoader);
		loaderMap.put(Protocol.NONE, localFileLoader);
		loaderMap.put(Protocol.FILE, localFileLoader);
		loaderMap.put(Protocol.CLASSPATH, classpathFileLoader);
		loaderMap.put(Protocol.SERVLET, servletFileLoader);
		loaderMap.put(Protocol.REDIS, redisFileLoader);
	}

	public Resp load(String path) {
		Protocol protocol = Protocol.analyze(path);
		FileLoader fileLoader = loaderMap.get(protocol);
		if (fileLoader == null)
			throw new UnsupportedOperationException("unsupported protocol: " + protocol);
		else
			return fileLoader.load(path);
	}

	public Resp load(String path, String sign) {
		Protocol protocol = Protocol.analyze(path);
		FileLoader fileLoader = loaderMap.get(protocol);
		if (fileLoader == null)
			throw new UnsupportedOperationException("unsupported protocol: " + protocol);
		else
			return fileLoader.load(path, sign);
	}
}