package io.terminus.galaxy.web.design.service;

import java.util.Map;

import cn.blmdz.hunt.protocol.Export;

public interface EcpSiteService {

    @Export
    Map<String, Object> listSitesWithLayouts();
}
