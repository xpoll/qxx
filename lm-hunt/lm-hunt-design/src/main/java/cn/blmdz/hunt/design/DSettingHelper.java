package cn.blmdz.hunt.design;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import cn.blmdz.hunt.design.medol.Page;
import cn.blmdz.hunt.design.medol.Site;
import cn.blmdz.hunt.design.service.PageService;
import cn.blmdz.hunt.design.service.SiteService;
import cn.blmdz.hunt.engine.SettingHelper;
import cn.blmdz.hunt.engine.model.App;
import cn.blmdz.hunt.engine.utils.Domains;

@Component
@Primary
public class DSettingHelper extends SettingHelper {
   private static final Logger log = LoggerFactory.getLogger(DSettingHelper.class);
   private static final Pattern DESIGN_PAGE_PATTERN = Pattern.compile("/design/pages/(\\d+)");
   private static final Pattern DESIGN_TEMPLATE_PATTERN = Pattern.compile("/design/shop-templates/(\\w+)/render");
   @Autowired
   private SiteService siteService;
   @Autowired
   private PageService pageService;

   public AppResult findApp(HttpServletRequest request) {
      App designApp = this.findAppForDesignMode(request);
      if(designApp != null) {
         return AppResult.sure(designApp);
      } else {
         String domain = Domains.getDomainFromRequest(request);
         Site site = this.siteService.findByDomain(domain);
         return site != null?AppResult.sure((App)this.setting.getAppMap().get(site.getApp())):super.findApp(request);
      }
   }

   private App findAppForDesignMode(HttpServletRequest request) {
      String referer = request.getHeader("Referer");
      if(Strings.isNullOrEmpty(referer)) {
         return null;
      } else {
         Matcher designPageMatch = DESIGN_PAGE_PATTERN.matcher(referer);
         if(designPageMatch.find()) {
            Long pageId = Long.valueOf(designPageMatch.group(1));
            Page page = this.pageService.find(pageId);
            log.info("catch request when design page {}, app is {}", pageId, page.getApp());
            return (App)this.setting.getAppMap().get(page.getApp());
         } else {
            Matcher designTemplateMatch = DESIGN_TEMPLATE_PATTERN.matcher(referer);
            if(designTemplateMatch.find()) {
               String app = designTemplateMatch.group(1);
               log.info("catch request when design template {}, app is {}", app);
               return (App)this.setting.getAppMap().get(app);
            } else {
               return null;
            }
         }
      }
   }
}
