package cn.blmdz.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.blmdz.web.dao.AlbumDao;
import cn.blmdz.web.entity.QxxAlbum;
import cn.blmdz.web.service.AlbumService;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumDao albumDao;

	@Override
	public QxxAlbum findById(Long id) {
		return albumDao.findById(id);
	}

	@Override
	public void update(QxxAlbum album) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(QxxAlbum album) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<QxxAlbum> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
