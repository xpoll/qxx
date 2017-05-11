package cn.blmdz.hbs.file;

import com.google.common.base.Charsets;

import lombok.Getter;
import lombok.Setter;

public interface FileLoader {

	Resp load(String path);

	Resp load(String path, String sign);

	public static class Resp {
		public static final Resp NOT_FOUND = new Resp();
		public static final Resp NOT_MODIFIED = new Resp();
		@Getter
		@Setter
		private boolean notFound;
		@Getter
		@Setter
		private boolean notModified;
		@Getter
		@Setter
		private String sign;
		@Getter
		@Setter
		private byte[] context;
		@Setter
		private String contextString;

		static {
			NOT_FOUND.notFound = true;
			NOT_MODIFIED.notModified = true;
		}

		public String asString() {
			if (contextString == null)
				contextString = new String(context, Charsets.UTF_8);
			return contextString;
		}

		public boolean modified() {
			return !notModified;
		}
	}
}