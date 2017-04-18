package cn.blmdz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cn.blmdz.enums.LayoutType;
import cn.blmdz.hunt.design.dao.SiteDao;
import cn.blmdz.hunt.design.medol.Site;
import cn.blmdz.hunt.engine.config.model.Render;
import cn.blmdz.hunt.engine.service.ConfigService;
import cn.blmdz.service.SitesService;

@Service
public class SitesServiceImpl implements SitesService {
    @Autowired
    private ConfigService configService;
    @Autowired
    private SiteDao siteDao;

    @Override
    public Map<String, Object> listSitesWithLayouts() {
        Map<String, Object> result = Maps.newHashMap();
        List<Site> sites = siteDao.listAll();
        result.put("sites", sites);
        List<Render.Layout> layouts = configService.listLayouts(LayoutType.SITE.name());
        result.put("layouts", layouts);
        return result;
    }

}
