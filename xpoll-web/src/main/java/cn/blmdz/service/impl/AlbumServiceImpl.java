package cn.blmdz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.blmdz.dao.AlbumDao;
import cn.blmdz.service.AlbumService;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumDao albumDao;
}
