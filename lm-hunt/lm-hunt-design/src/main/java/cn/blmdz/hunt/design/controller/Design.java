package cn.blmdz.hunt.design.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.net.MediaType;

import cn.blmdz.hunt.common.exception.NotFound404Exception;
import cn.blmdz.hunt.design.container.DPageRender;
import cn.blmdz.hunt.design.controller.ext.DesignHook;
import cn.blmdz.hunt.design.medol.Page;
import cn.blmdz.hunt.design.medol.Site;
import cn.blmdz.hunt.design.service.PageService;
import cn.blmdz.hunt.design.service.SiteService;
import cn.blmdz.hunt.design.util.PagePartPath;
import cn.blmdz.hunt.design.util.PagePartScope;
import cn.blmdz.hunt.engine.RenderConstants;
import cn.blmdz.hunt.engine.service.ConfigService;

@Controller
@RequestMapping({ "/design" })
public class Design {
	private static final Logger log = LoggerFactory.getLogger(Design.class);
	@Autowired
	private SiteService siteService;
	@Autowired
	private PageService pageService;
	@Autowired
	private DPageRender dPageRender;
	@Autowired
	private ConfigService configService;
	@Autowired(required = false)
	private DesignHook designHook;

	@RequestMapping(value = "/sites/{siteId}", method = RequestMethod.GET)
	public String design(@PathVariable Long siteId,
			@RequestParam(required = false) Long pageId, Map<String, Object> context) {
		Map<String, Object> editorContext = Maps.newHashMap();
		Site site = siteService.findById(siteId);
		if (site == null) {
			log.error("can not find site when edit template, siteId: {}", siteId);
			throw new NotFound404Exception("未找到对应的站点");
		} else {
			if (designHook != null) {
				designHook.design(site, context);
			}

			editorContext.put("site", site);
			List<Page> pages = pageService.listBySite(siteId);
			if (pages != null && !pages.isEmpty()) {
				Map<String, Page> pageMap = Maps.newHashMap();
				Page currentPage = null;

				for (Page page : pages) {
					pageMap.put(page.getPath(), page);
					if (Objects.equal(pageId, page.getId())) {
						currentPage = page;
					}
				}

				editorContext.put("pages", pageMap);
				Page indexPage = (Page) pageMap.get(Strings.isNullOrEmpty(site
						.getIndex()) ? "index" : site.getIndex());
				editorContext.put("indexPage", indexPage);
				editorContext.put("currentPage", MoreObjects.firstNonNull(currentPage, MoreObjects.firstNonNull(indexPage, pages.get(0))));
			}

			context.put("editorContext", editorContext);
			context.put("app", site.getApp());
			context.put("title", "站点编辑");
			return configService.getDefaultFrontConfig().getRender()
					.getEditorLayout();
		}
	}

	@RequestMapping(value = "/pages/{pageId}", method = RequestMethod.GET)
	public void page(@PathVariable Long pageId, @RequestParam Map<String, Object> context,
			HttpServletResponse response, HttpServletRequest request) {
		if (designHook != null)
			designHook.page(pageId, context);

		context.put(RenderConstants.DESIGN_MODE, Boolean.valueOf(true));
		String pageHtml = dPageRender.render(pageId, context);
		response.setContentType(MediaType.HTML_UTF_8.toString());

		try {
			if (pageHtml.startsWith("forward:")) {
				String path = pageHtml.substring("forward:".length());
				response.getWriter().write("<br><br><br><br>");
				response.getWriter().write("此页面是个定向页，被定向到[" + path + "]，不能在站点中进行装修。");
			} else {
				response.getWriter().write(pageHtml);
			}
		} catch (IOException e) {
		}

	}

	@RequestMapping(value = "/custom-pages", method = RequestMethod.GET)
	public String customPage(@RequestParam String url,
			@RequestParam String pagePath, Map<String, Object> context) {
		if (designHook != null) {
			String result = designHook.customPage(url, pagePath);
			if (!Strings.isNullOrEmpty(result)) {
				return result;
			}
		}

		Map<String, Object> editorContext = Maps.newHashMap();
		PagePartPath pagePartPath = new PagePartPath(pagePath);
		switch (PagePartScope.fromString(pagePartPath.getScope())) {
		case NAIVE:
			editorContext.put("mode", "PAGE_PARTS");
			editorContext.put("noPage", Boolean.valueOf(true));
			editorContext.put("detailType", Integer.valueOf(0));
			editorContext.put("naiveUrl", url);
			context.put("editorContext", editorContext);
			context.put("title", "页面编辑");
			return configService.getDefaultFrontConfig().getRender()
					.getEditorLayout();
		case PAGE:
			Long pageId = Long.valueOf(pagePartPath.getKey());
			Site site = siteService.findByPageId(pageId);
			return "redirect:/design/sites/" + site.getId() + "?pageId=" + pageId;
		default:
			throw new IllegalStateException();
		}
	}
}
