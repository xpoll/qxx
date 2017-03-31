package site.blmdz.base.session.service;

import java.util.Map;

/**
 * 序列号
 * @author lm
 * @date 2016年11月4日 下午10:33:57
 */
public interface Serialize {

	/**
	 * 序列化
	 * @author lm
	 * @date 2016年11月4日 下午10:33:54
	 * @param o
	 * @return String
	 */
	String serialize(Object o);
	
	/**
	 * 反序列化
	 * @author lm
	 * @date 2016年11月4日 下午10:34:57
	 * @param o
	 * @return Map<String,Object>
	 */
	Map<String, Object> deserialize(String o);
}
