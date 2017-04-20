package cn.blmdz.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.blmdz.entity.QxxImage;
import cn.blmdz.model.UploadDto;

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
	
	/**
	 * 上传图片
	 * 假设文件夹已存在, 调用前要判断好文件夹是否存在
	 * @param id
	 * @param aid
	 * @param name
	 * @param file
	 */
	UploadDto upload(Long id, Long aid, String name, MultipartFile file);
}
