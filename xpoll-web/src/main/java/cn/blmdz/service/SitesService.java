package cn.blmdz.service;

import java.util.Map;

import cn.blmdz.hunt.protocol.Export;

public interface SitesService {

	@Export
    Map<String, Object> listSitesWithLayouts();
}
