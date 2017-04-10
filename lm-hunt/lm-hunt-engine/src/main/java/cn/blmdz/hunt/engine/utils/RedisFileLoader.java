package cn.blmdz.hunt.engine.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import cn.blmdz.hunt.engine.dao.RedisFileDao;

@Component
public class RedisFileLoader implements FileLoader {

	@Autowired
	private RedisFileDao redisFileDao;

	@Override
	public Resp load(String path) {
		return load(path, null);
	}

	@Override
	public Resp load(String path, String sign) {
		Long updateAt = redisFileDao.getUpdateTime(path);
		if (updateAt == null)
			return FileLoader.Resp.NOT_FOUND;
		if (!Strings.isNullOrEmpty(sign)) {
			Long oldValue = Long.valueOf(sign);
			if (Objects.equal(oldValue, updateAt))
				return FileLoader.Resp.NOT_MODIFIED;
		}
		String content = redisFileDao.getContent(path);
		FileLoader.Resp resp = new FileLoader.Resp();
		resp.setContextString(content);
		resp.setSign(updateAt.toString());
		return resp;
	}

}