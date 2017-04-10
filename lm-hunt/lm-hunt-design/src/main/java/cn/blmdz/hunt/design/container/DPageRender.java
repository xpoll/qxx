package cn.blmdz.hunt.design.container;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import cn.blmdz.hunt.common.exception.NotFound404Exception;
import cn.blmdz.hunt.design.medol.Page;
import cn.blmdz.hunt.design.medol.Page.Type;
import cn.blmdz.hunt.design.medol.Site;
import cn.blmdz.hunt.design.service.PageService;
import cn.blmdz.hunt.design.service.SiteService;
import cn.blmdz.hunt.design.util.PagePartPath;
import cn.blmdz.hunt.design.util.PagePartScope;
import cn.blmdz.hunt.engine.PageRender;
import cn.blmdz.hunt.engine.ThreadVars;
import cn.blmdz.hunt.engine.config.model.Render;
import cn.blmdz.hunt.engine.config.model.Render.Layout;
import cn.blmdz.hunt.engine.service.ConfigService;

@Component
@Primary
public class DPageRender extends PageRender {
	private static final Logger log = LoggerFactory
			.getLogger(DPageRender.class);
	@Autowired
	private SiteService siteService;
	@Autowired
	private PageService pageService;
	@Autowired
	private ConfigService configService;

	public String render(String domain, String path, Map<String, Object> context) {
		if (this.isNaiveRender(path)) {
			return this._naiveRender(null, path, context);
		} else {
			Site site = this.siteService.findByDomain(domain);
			if (site == null) {
				return this._naiveRender(null, path, context);
			} else {
				if (Objects.equal(path, "index")
						&& !Strings.isNullOrEmpty(site.getIndex())) {
					path = site.getIndex();
				}

				Page page = this.pageService.find(site.getId(), path);
				return page == null ? this._naiveRender(site, path, context)
						: this._pageRender(site, page, context);
			}
		}
	}

	public String render(Long siteId, String path, Map<String, Object> context) {
		Site site = this.siteService.findById(siteId);
		if (site == null) {
			log.warn("site not found when render, siteId: {}, path: {}",
					siteId, path);
			throw new NotFound404Exception("site not found: " + siteId);
		} else {
			if (Objects.equal(path, "index")
					&& !Strings.isNullOrEmpty(site.getIndex())) {
				path = site.getIndex();
			}

			Page page = this.pageService.find(siteId, path);
			return page == null ? this._naiveRender(site, path, context) : this
					._pageRender(site, page, context);
		}
	}

	public String render(Long pageId, Map<String, Object> context) {
		Page page = this.pageService.find(pageId);
		if (page == null) {
			log.warn("page not found when render, pageId: {}", pageId);
			throw new NotFound404Exception("page not found: " + pageId);
		} else {
			Site site = this.siteService.findById(page.getSiteId());
			return this._pageRender(site, page, context);
		}
	}

	private String _naiveRender(Site site, String path, Map<String, Object> context) {
		if (site != null) {
			this.useSite(site, context);
			context.put("_SITE_", site);
		}

		return this.naiveRender(path, context);
	}

	private String _pageRender(Site site, Page page, Map<String, Object> context) {
		this.useSite(site, context);
		if (page.getType() == Type.LINK) {
			return "forward:" + page.getLink();
		} else {
			String path = this.getLayoutPath(site, page);
			context.put("_PAGE_", page);
			context.put(
					"_DESIGNABLE_",
					new PagePartPath(PagePartScope.PAGE.toString(), page
							.getId(), (String) null));
			return this.naiveRender(path, page.getPath(), context);
		}
	}

	private String getLayoutPath(Site site, Page page) {
		String path;
		if (site.getLayout().startsWith("_template:")) {
			path = "redis:app:" + site.getApp() + ":template:"
					+ site.getLayout() + ":path:"
					+ (page.isImmutable() ? page.getPath() : "default_layout")
					+ ":content";
		} else {
			Layout layout = this.configService.findLayout(site.getApp(),
					site.getLayout());
			String layoutRoot = layout.getRoot();
			if (Objects.equal(layoutRoot, "/")) {
				layoutRoot = "";
			}

			path = layoutRoot + "/"
					+ (page.isImmutable() ? page.getPath() : "default_layout");
		}

		return path;
	}

	private boolean isNaiveRender(String path) {
		Render render = this.configService.getFrontConfig(
				ThreadVars.getAppKey()).getRender();
		if (render == null) {
			return true;
		} else {
			if (!CollectionUtils.isEmpty(render.getPrefixs())) {
				for (String prefix : render.getPrefixs()) {
					if (path.toLowerCase().startsWith(prefix.toLowerCase())) {
						return true;
					}
				}
			}

			return false;
		}
	}

	private void useSite(Site site, Map<String, Object> context) {
		ThreadVars.setApp(this.configService.getApp(site.getApp()));
		context.put("_SITE_", site);
		if (context.get("_MAIN_SITE_") == null) {
			context.put("_MAIN_SITE_", site);
		}

	}
}
