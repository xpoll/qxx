package cn.blmdz.web.util;

import org.springframework.beans.BeanUtils;

import cn.blmdz.web.entity.QxxUser;
import cn.blmdz.web.model.User;

public class UserMaker {

	public static User from(QxxUser qxxUser) {
		User user = new User();
		BeanUtils.copyProperties(qxxUser, user);
		return user;
	}

}
