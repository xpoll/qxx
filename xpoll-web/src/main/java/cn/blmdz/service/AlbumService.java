package cn.blmdz.service;

import java.util.List;

import cn.blmdz.entity.QxxAlbum;

/**
 * 相册
 * @author lm
 */
public interface AlbumService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	QxxAlbum findById(Long id);
	
	/**
	 * 修改
	 * @param album
	 */
	void update(QxxAlbum album);
	
	/**
	 * 删除
	 * @param id
	 */
	void delete(Long id);
	
	/**
	 * 创建
	 * @param album
	 */
	void create(QxxAlbum album);
	
	List<QxxAlbum> findAll();
}
