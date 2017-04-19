package cn.blmdz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.blmdz.dao.ImageDao;
import cn.blmdz.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageDao imageDao;
}
