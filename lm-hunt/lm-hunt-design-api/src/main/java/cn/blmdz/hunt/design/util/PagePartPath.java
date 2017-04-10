package cn.blmdz.hunt.design.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

import lombok.Getter;
import lombok.Setter;

public class PagePartPath {
	@Getter
	@Setter
	private String scope;
	@Getter
	@Setter
	private String key;
	@Getter
	@Setter
	private String path;
	private static Pattern regex = Pattern.compile("^(\\[(\\w+)/(\\w+)\\])?([^\\[\\]]+)?$");
	
	public PagePartPath(String fullPath) {
		Matcher m = regex.matcher(fullPath);
		if (!(m.matches())) {
			throw new IllegalArgumentException("path format error");
		}
		if (m.group(1) != null) {
			this.scope = m.group(2).toUpperCase();
			this.key = m.group(3);
		}
		this.path = Strings.nullToEmpty(m.group(4));
	}
	
	public PagePartPath(String scope, Object key, String path) {
		this.key = ((key == null) ? null : key.toString());
		this.scope = scope;
		this.path = Strings.nullToEmpty(path);
	}

	@Override
	public String toString() {
		if (Strings.isNullOrEmpty(this.scope)) {
			return this.path;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(this.scope);
		if (!(Strings.isNullOrEmpty(this.key))) {
			sb.append("/").append(this.key);
		}
		sb.append("]").append(this.path);
		return sb.toString();
	}
}
