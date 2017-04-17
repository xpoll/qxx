package cn.blmdz.hunt.design.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import cn.blmdz.common.exception.JsonResponseException;
import cn.blmdz.hunt.design.controller.ext.SitesHook;
import cn.blmdz.hunt.design.medol.Page;
import cn.blmdz.hunt.design.medol.Site;
import cn.blmdz.hunt.design.service.PageService;
import cn.blmdz.hunt.design.service.SiteService;
import cn.blmdz.hunt.engine.config.model.Render.Layout;
import cn.blmdz.hunt.engine.service.ConfigService;

@RequestMapping({ "/api/design/sites" })
@Controller
public class Sites {
	private static final Logger log = LoggerFactory.getLogger(Sites.class);
	@Autowired
	private SiteService siteService;
	@Autowired
	private PageService pageService;
	@Autowired
	private ConfigService configService;
	@Autowired(required = false)
	private SitesHook sitesHook;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void create(@RequestBody Site site) {
		Site exists = siteService.findByDomain(site.getDomain());
		if (exists != null) {
			throw new JsonResponseException(400, "站点所用的域名已经存在");
		} else {
			fillSite(site);
			if (sitesHook != null) {
				sitesHook.create(site);
			}

			siteService.create(site, false);
		}
	}

	@RequestMapping(value = "/{siteId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void update(@PathVariable Long siteId, @RequestBody Site site) {
		site.setDomain(Strings.emptyToNull(site.getDomain()));
		if (site.getDomain() != null) {
			Site old = siteService.findById(siteId);
			if (!Objects.equal(old.getDomain(), site.getDomain())) {
				Site exists = siteService.findByDomain(site.getDomain());
				if (exists != null) {
					throw new JsonResponseException(400, "站点所用的域名已经存在");
				}
			}
		}

		fillSite(site);
		if (sitesHook != null) {
			sitesHook.update(site);
		}

		siteService.update(site);
	}

	@RequestMapping(value = "/{siteId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(@PathVariable Long siteId) {
		if (sitesHook != null) {
			Site site = siteService.findById(siteId);
			if (site == null) {
				return;
			}

			sitesHook.delete(site);
		}

		siteService.delete(siteId);
	}

	@RequestMapping(value = "/{siteId}/set-index", method =  RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void setIndex(@PathVariable Long siteId, @RequestParam String path) {
		Site old = siteService.findById(siteId);
		if (old == null) {
			log.warn("site for id [{}] not found when set index", siteId);
		} else {
			if (sitesHook != null) {
				sitesHook.setIndex(old, path);
			}

			if (!Objects.equal(old.getIndex(), path)) {
				Page page = pageService.find(siteId, path);
				if (page == null) {
					throw new JsonResponseException(400, "路径对应的页面不存在");
				}

				old.setIndex(path);
				siteService.update(old);
			}

		}
	}

	@RequestMapping(value = "/{siteId}/clear-index", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void clearIndex(@PathVariable Long siteId) {
		Site old = siteService.findById(siteId);
		if (old == null) {
			log.warn("site for id [{}] not found when set index", siteId);
		} else {
			if (sitesHook != null) {
				sitesHook.clearIndex(old);
			}

			siteService.clearIndex(siteId);
		}
	}

	@RequestMapping(value = "/{siteId}/release", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void release(@PathVariable Long siteId) {
		Site old = siteService.findById(siteId);
		if (old == null) {
			log.warn("site for id [{}] not found when set release", siteId);
		} else {
			if (sitesHook != null) {
				sitesHook.release(old);
			}

			siteService.release(siteId);
		}
	}

	@RequestMapping(value = "/{siteId}/pages", method =  RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Page> listPages(@PathVariable Long siteId) {
		Site old = siteService.findById(siteId);
		if (old == null) {
			log.warn("site for id [{}] not found when list pages", siteId);
			return Collections.emptyList();
		} else {
			if (sitesHook != null) {
				sitesHook.listPages(old);
			}

			List<Page> pages = pageService.listBySite(siteId);
			return pages;
		}
	}

	private void fillSite(Site site) {
//		site.setUserId(UserUtil.getUserId()); TODO
		site.setUserId(1L);
		Layout layout = configService.findLayout(site.getApp(),
				site.getLayout());
		if (layout == null) {
			throw new JsonResponseException(400, "layout 未找到");
		} else {
			site.setLayoutName(layout.getName());
		}
	}
}
