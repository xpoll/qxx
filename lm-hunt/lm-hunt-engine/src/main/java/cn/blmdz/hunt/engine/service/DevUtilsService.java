package cn.blmdz.hunt.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.blmdz.hunt.engine.i18n.I18nResourceBundle;
import cn.blmdz.hunt.protocol.Export;

@Service
public class DevUtilsService {
	@Autowired
	private I18nResourceBundle i18nResourceBundle;

	@Export
	public void clearI18nCache() {
		this.i18nResourceBundle.clearAllCache();
	}
}
