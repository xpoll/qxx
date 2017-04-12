package io.terminus.galaxy.web.design.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import cn.blmdz.hunt.common.util.JedisTemplate;
import cn.blmdz.hunt.design.dao.SiteDao;
import cn.blmdz.hunt.design.medol.Site;
import cn.blmdz.hunt.engine.config.model.Render;
import cn.blmdz.hunt.engine.service.ConfigService;

@Service
public class EcpSiteServiceImpl implements EcpSiteService {
    @Autowired
    private ConfigService configService;
    @Autowired(required = false)
    private SiteDao siteDao;
    @Autowired
    @Qualifier("pampasJedisTemplate")
    private JedisTemplate jedisTemplate;


    @Override
    public Map<String, Object> listSitesWithLayouts() {
        Map<String, Object> result = Maps.newHashMap();
        List<Site> sites = siteDao.listAll();
        result.put("sites", sites);
        List<Render.Layout> layouts = configService.listLayouts(EcpLayoutType.SITE.name());
        result.put("layouts", layouts);
        return result;
    }

}
