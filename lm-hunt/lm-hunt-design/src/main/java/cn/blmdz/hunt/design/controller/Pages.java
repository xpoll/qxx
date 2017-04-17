package cn.blmdz.hunt.design.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;

import cn.blmdz.common.exception.JsonResponseException;
import cn.blmdz.common.util.JsonMapper;
import cn.blmdz.hunt.design.controller.ext.PagesHook;
import cn.blmdz.hunt.design.medol.Page;
import cn.blmdz.hunt.design.medol.Page.Type;
import cn.blmdz.hunt.design.medol.Site;
import cn.blmdz.hunt.design.service.PageService;
import cn.blmdz.hunt.design.service.SiteService;

@Controller
@RequestMapping({ "/api/design/pages" })
public class Pages {
	@Autowired
	private PageService pageService;
	@Autowired
	private SiteService siteService;
	@Autowired(required = false)
	private PagesHook pagesHook;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Long create(@RequestParam Long siteId, @RequestParam String pageJson) {
		Site site = siteService.findById(siteId);
		if (site == null) {
			throw new JsonResponseException(400, "site not found");
		} else {
			Page page = JsonMapper.nonEmptyMapper().fromJson(pageJson, Page.class);
			page.setSiteId(siteId);
			page.setApp(site.getApp());
			page.setPath(page.getPath().toLowerCase());
			if (page.getType() == null) {
				page.setType(Type.PAGE);
			}

			if (pagesHook != null) {
				pagesHook.create(page);
			}

			return pageService.create(siteId, page);
		}
	}

	@RequestMapping(value = "/{pageId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void update(@PathVariable Long pageId, @RequestParam String pageJson) {
		Page exists = pageService.find(pageId);
		if (exists != null) {
			Page page = (Page) JsonMapper.nonEmptyMapper().fromJson(pageJson, Page.class);
			page.setSiteId(exists.getSiteId());
			page.setId(pageId);
			page.setPath(page.getPath().toLowerCase());
			if (pagesHook != null) {
				pagesHook.update(page);
			}

			if (!Objects.equal(exists.getPath(), page.getPath())) {
				for (Page existedPage : pageService.listBySite(page
						.getSiteId())) {
					if (existedPage.getPath().equals(page.getPath())) {
						throw new JsonResponseException(400, "路径已经存在，不能重复");
					}
				}
			}

			pageService.update(page);
		}
	}

	@RequestMapping(value = "/{pageId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(@PathVariable Long pageId) {
		if (pagesHook != null) {
			Page page = pageService.find(pageId);
			if (page == null) {
				return;
			}

			pagesHook.delete(page);
		}

		pageService.delete(pageId);
	}
}
