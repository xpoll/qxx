package cn.blmdz.service;

import java.util.List;

import cn.blmdz.entity.QxxImage;

/**
 * 图片
 * @author lm
 */
public interface ImageService {

	/**
	 * 创建
	 * @param image
	 */
	void create(QxxImage image);
	
	/**
	 * 更新
	 * @param id
	 * @param name
	 */
	void update(Long id, String name);
	
	/**
	 * 删除
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 根据相册ID查找图片
	 * @param id
	 * @param size
	 * @param num
	 * @return
	 */
	List<QxxImage> page(Long id, Integer size, Integer num);
	
	/**
	 * 将图片移动至另一个相册
	 * @param id
	 * @param aid
	 */
	void move(Long id, Long aid);
}
