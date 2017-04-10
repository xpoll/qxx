package cn.blmdz.hunt.design.controller.ext;

import java.util.Map;

public interface PagePartsHook {
	void save(String path, Map<String, String> pageParts);

	void release(String path);

	void delete(String path);

	void move(String srcPath, String destPath);
}
