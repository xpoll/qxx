package cn.blmdz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.blmdz.dao.ImageDao;
import cn.blmdz.entity.QxxImage;
import cn.blmdz.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageDao imageDao;

	@Override
	public void create(QxxImage image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Long id, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<QxxImage> findByAid(Long id, Integer size, Integer num) {
		// TODO Auto-generated method stub
		return null;
	}
}
