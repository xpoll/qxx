package cn.blmdz.hbs.hbs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.github.jknack.handlebars.io.AbstractTemplateLoader;
import com.github.jknack.handlebars.io.StringTemplateSource;
import com.github.jknack.handlebars.io.TemplateSource;

import cn.blmdz.hbs.file.FileLoader;
import cn.blmdz.hbs.file.FileLoaderHelper;
import cn.blmdz.hbs.file.Protocol;
import cn.blmdz.hbs.util.MapBuilder;

public class GreatTemplateLoader extends AbstractTemplateLoader {
	private static final String SUFFIX = ".hbs";
	private static final Map<Protocol, String> PROTOCOL_SUFFIXS = MapBuilder.<Protocol, String>of()
			.put(Protocol.HTTP, SUFFIX)
			.put(Protocol.HTTPS, SUFFIX)
			.put(Protocol.SERVLET, SUFFIX)
			.put(Protocol.CLASSPATH, SUFFIX)
			.put(Protocol.NONE, SUFFIX)
			.put(Protocol.FILE, SUFFIX)
			.put(Protocol.ZK, "")
			.put(Protocol.REDIS, "")
			.map();
	private FileLoaderHelper fileLoaderHelper;

	public GreatTemplateLoader(FileLoaderHelper fileLoaderHelper) {
		this.fileLoaderHelper = fileLoaderHelper;
	}

	public TemplateSource sourceAt(String location) throws IOException {
		Validate.notEmpty(location, "The uri is required.", new Object[0]);
		FileLoader.Resp resp = this.getResource(location);
		if (resp.isNotFound()) {
			throw new FileNotFoundException(location + " not found");
		} else {
			return new StringTemplateSource(location, resp.asString());
		}
	}

	private FileLoader.Resp getResource(String location) throws IOException {
		boolean isComponent = location.startsWith("component:");
		if (isComponent) {
			location = location.substring("component:".length());
		}

		Protocol protocol = Protocol.analyze(location);
		if (protocol == Protocol.NONE) {
			String home = "classpath:apps/";
			location = home + (isComponent ? "components/" : "views/") + this.normalize(location);
		}

		return this.fileLoaderHelper.load(location + PROTOCOL_SUFFIXS.get(protocol));
	}
}