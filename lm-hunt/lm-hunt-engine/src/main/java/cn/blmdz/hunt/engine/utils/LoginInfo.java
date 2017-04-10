package cn.blmdz.hunt.engine.utils;

import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

import cn.blmdz.hunt.common.util.Joiners;
import cn.blmdz.hunt.common.util.Splitters;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LoginInfo {
	public static final String SESSION_ID = "pms_id";
	private static final String SECURITY_KEY = "中文密钥也挺好";
	@Getter
	private final Long userId;
	@Getter
	private final String ip;

	public String toCookieKey() {
		String data = BaseEncoding.base64()
				.encode(Joiners.COLON.join(userId, ip, new Object[0]).getBytes(Charsets.UTF_8));
		String hash = Hashing.sha1().hashString(security(data), Charsets.UTF_8).toString();
		return data + "--" + hash;
	}

	public static LoginInfo fromCookieKey(String key) {
		String[] keys = key.split("--");
		String data = keys[0];
		String hash = keys[1];
		if (!Objects.equal(Hashing.sha1().hashString(security(data), Charsets.UTF_8).toString(), hash))
			throw new IllegalArgumentException("hash verify failed");
		List<String> infos = Splitters.COLON
				.splitToList(new String(BaseEncoding.base64().decode(data), Charsets.UTF_8));
		return new LoginInfo(Long.valueOf(infos.get(0)), infos.get(1));
	}

	private static String security(String data) {
		return SECURITY_KEY + data + SECURITY_KEY;
	}
}