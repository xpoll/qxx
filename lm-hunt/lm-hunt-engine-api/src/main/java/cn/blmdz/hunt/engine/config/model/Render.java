package cn.blmdz.hunt.engine.config.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Data;

@Data
public class Render implements Serializable {
	private static final long serialVersionUID = 2784212452907126587L;

	public static final String DEFAULT_LAYOUT = "default_layout";
	private Set<String> prefixs;
	private Map<String, Layout> layouts;
	private String editorLayout;

	public void merge(Render mergedRender) {
		if ((mergedRender.getPrefixs() != null) && (!mergedRender.getPrefixs().isEmpty())) {
			if (this.prefixs == null) {
				this.prefixs = Sets.newHashSet();
			}
			this.prefixs.addAll(mergedRender.getPrefixs());
		}
		if ((mergedRender.getLayouts() != null) && (!mergedRender.getLayouts().isEmpty())) {
			if (this.layouts == null) {
				this.layouts = Maps.newHashMap();
			}
			this.layouts.putAll(mergedRender.getLayouts());
		}
		if (!Strings.isNullOrEmpty(mergedRender.getEditorLayout()))
			this.editorLayout = mergedRender.getEditorLayout();
	}

	@Data
	public static class Layout implements Serializable {
		private static final long serialVersionUID = 4298614254776376098L;
		private String key;
		private String app;
		private String type;
		private String root;
		private String name;
		private String desc;
	}
}