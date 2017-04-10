package cn.blmdz.hunt.design.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import cn.blmdz.hunt.design.dao.PagePartDao;

@Service
public class PagePartServiceImpl implements PagePartService {
	@Autowired
	private PagePartDao pagePartDao;

	@Override
	public Map<String, String> findByKey(String app, String key) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(app),
				"app can not be empty");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(key),
				"key can not be empty");
		return pagePartDao.findByKey(app, key, false);
	}

	@Override
	public boolean isReleaseExists(String app, String key) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(app),
				"app can not be empty");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(key),
				"key can not be empty");
		return pagePartDao.isExists(app, key, true);
	}

	@Override
	public Map<String, String> findReleaseByKey(String app, String key) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(app),
				"app can not be empty");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(key),
				"key can not be empty");
		return pagePartDao.findByKey(app, key, true);
	}

	@Override
	public void put(String app, String key, Map<String, String> parts) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(app),
				"app can not be empty");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(key),
				"key can not be empty");
		Preconditions.checkNotNull(parts, "parts can not be null");
		pagePartDao.put(app, key, parts, false);
	}

	@Override
	public void put(String app, String key, String partKey, String part) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(app),
				"app can not be empty");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(key),
				"key can not be empty");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(partKey),
				"partKey can not be empty");
		pagePartDao.put(app, key, partKey, part, false);
	}

	@Override
	public void replace(String app, String key, Map<String, String> parts) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(app),
				"app can not be empty");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(key),
				"key can not be empty");
		Preconditions.checkNotNull(parts, "parts can not be null");
		pagePartDao.replace(app, key, parts, false);
	}

	@Override
	public void release(String app, String key) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(app),
				"app can not be empty");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(key),
				"key can not be empty");
		Map<String, String> draft = pagePartDao.findByKey(app, key, false);
		pagePartDao.replace(app, key, draft, true);
	}

	@Override
	public void delete(String app, String key) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(app),
				"app can not be empty");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(key),
				"key can not be empty");
		pagePartDao.delete(app, key);
	}
}
