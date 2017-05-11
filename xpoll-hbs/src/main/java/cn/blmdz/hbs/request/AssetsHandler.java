package cn.blmdz.hbs.request;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;

import cn.blmdz.hbs.file.FileLoader;
import cn.blmdz.hbs.file.FileLoaderHelper;
import cn.blmdz.hbs.util.MimeTypes;
import cn.blmdz.hbs.util.Splitters;
import lombok.extern.slf4j.Slf4j;

/**
 * 资源文件管理
 */
@Slf4j
@Component
public class AssetsHandler {
	@Autowired
	private FileLoaderHelper fileLoaderHelper;

	/**
	 * 不是媒体或静态文件返回false <br>
	 * 有资源直接write（404设置状态）返回true 
	 */
	public boolean handle(String path, HttpServletResponse response) {
		String lastPath = Iterables.getLast(Splitters.SLASH.split(path));
		List<String> fileInfo = Splitters.DOT.splitToList(lastPath);
		if (fileInfo.size() == 1)
			return false;

		response.setContentType(MimeTypes.getType(Iterables.getLast(fileInfo)));

		String realPath = "classpath:" + path;
		FileLoader.Resp resp = fileLoaderHelper.load(realPath);
		if (resp.isNotFound()) {
			if (log.isDebugEnabled()) {
				log.debug("Assets not found, path: [{}]", path);
			}
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return true;
		}
		response.setContentLength(resp.getContext().length);

		try {
			response.getOutputStream().write(resp.getContext());
		} catch (IOException e) {
		}

		return true;
	}
}