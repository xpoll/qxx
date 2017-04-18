package cn.blmdz.service;

import cn.blmdz.entity.QxxUser;

public interface UserService {
	
	QxxUser login(String owner, String pwd);

	QxxUser findByid(Long id);

	boolean updatePwd(String old, String snew);
}
