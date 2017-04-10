package cn.blmdz.hunt.design.controller.ext;

import java.util.Map;

import cn.blmdz.hunt.design.medol.Site;

public interface DesignHook {
	void design(Site var1, Map<String, Object> var2);

	void page(Long var1, Map<String, Object> var2);

	String customPage(String var1, String var2);
}
