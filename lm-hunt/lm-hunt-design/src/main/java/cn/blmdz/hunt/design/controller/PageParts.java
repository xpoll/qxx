package cn.blmdz.hunt.design.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;

import cn.blmdz.hunt.common.exception.JsonResponseException;
import cn.blmdz.hunt.common.util.JsonMapper;
import cn.blmdz.hunt.design.controller.ext.PagePartsHook;
import cn.blmdz.hunt.design.service.PagePartService;

@Controller
@RequestMapping({ "/api/design/page-parts" })
public class PageParts {
	private static final Logger log = LoggerFactory.getLogger(PageParts.class);
	@Autowired
	private PagePartService pagePartService;
	@Autowired(required = false)
	private PagePartsHook pagePartsHook;
	private static final JavaType JAVA_TYPE = JsonMapper.nonEmptyMapper()
			.createCollectionType(HashMap.class,
					new Class[] { String.class, String.class });

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void save(@RequestParam String app, @RequestParam String path,
			@RequestParam String parts,
			@RequestParam(required = false) String extraParts) {
		try {
			Map<String, String> pageParts = JsonMapper.nonEmptyMapper().fromJson(parts, JAVA_TYPE);
			if (pagePartsHook != null) {
				pagePartsHook.save(path, pageParts);
			}

			pagePartService.replace(app, path, pageParts);
			if (!Strings.isNullOrEmpty(extraParts)) {
				@SuppressWarnings("unchecked")
				Map<String, Map<String, String>> extraPartsMap = JsonMapper
						.nonEmptyMapper().fromJson(extraParts, Map.class);

				for (String extraPath : extraPartsMap.keySet()) {
					pagePartService.put(app, extraPath, extraPartsMap.get(extraPath));
				}

			}
		} catch (Exception var9) {
			log.error("error when save page parts, path: {} cause: {}",
					new Object[] { path, var9.getMessage(), var9 });
			throw new JsonResponseException("pageparts.save.fail");
		}
	}

	@RequestMapping(value = "release", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void release(@RequestParam String app, @RequestParam String path,
			@RequestParam(required = false) String extraPaths) {
		if (pagePartsHook != null) {
			pagePartsHook.release(path);
		}

		try {
			pagePartService.release(app, path);
			if (!Strings.isNullOrEmpty(extraPaths)) {
				@SuppressWarnings("unchecked")
				List<String> extraPathList = JsonMapper.nonEmptyMapper()
						.fromJson(extraPaths, List.class);
				for (String extraPath : extraPathList) {
					pagePartService.release(app, extraPath);
				}

			}
		} catch (Exception e) {
			log.error("error when save page parts, path: {} cause: {}", new Object[] { path, e.getMessage(), e });
			throw new JsonResponseException("pageparts.release.fail");
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(@RequestParam String app, @RequestParam String path) {
		if (pagePartsHook != null) {
			pagePartsHook.delete(path);
		}

		try {
			pagePartService.delete(app, path);
		} catch (Exception e) {log.error("error when save page parts, path: {} cause: {}", new Object[] { path, e.getMessage(), e });
			throw new JsonResponseException("pageparts.delete.fail");
		}
	}

	@RequestMapping(value = "move", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void move(@RequestParam String app, @RequestParam String srcPath,
			@RequestParam String destPath) {
		try {
			if (pagePartsHook != null) {
				pagePartsHook.move(srcPath, destPath);
			}

			Map<String, String> pageParts = pagePartService.findByKey(app, srcPath);
			if (pageParts != null && !pageParts.isEmpty()) {
				pagePartService.replace(app, destPath, pageParts);
			}

		} catch (Exception e) {
			log.error("error when move page parts, src path: {},dest path:{} cause: {}", new Object[] { srcPath, destPath, e.getMessage(), e });
			throw new JsonResponseException("pageparts.move.fail");
		}
	}
}
