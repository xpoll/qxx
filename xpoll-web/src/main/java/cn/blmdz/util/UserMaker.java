package cn.blmdz.util;

import org.springframework.beans.BeanUtils;

import cn.blmdz.entity.QxxUser;
import cn.blmdz.model.User;

public class UserMaker {

	public static User from(QxxUser qxxUser) {
		User user = new User();
		BeanUtils.copyProperties(qxxUser, user);
		return user;
	}

}
