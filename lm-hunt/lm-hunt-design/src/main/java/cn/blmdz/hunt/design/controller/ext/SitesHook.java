package cn.blmdz.hunt.design.controller.ext;

import cn.blmdz.hunt.design.medol.Site;

public interface SitesHook {
	void create(Site var1);

	void update(Site var1);

	void delete(Site var1);

	void setIndex(Site var1, String var2);

	void clearIndex(Site var1);

	void release(Site var1);

	void listPages(Site var1);
}
