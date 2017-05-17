package cn.blmdz.web.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

import cn.blmdz.common.util.EncryptUtil;
import cn.blmdz.common.util.UserUtil;
import cn.blmdz.hbs.exception.GlobalException;
import cn.blmdz.web.dao.UserDao;
import cn.blmdz.web.entity.QxxUser;
import cn.blmdz.web.enums.AuthType;
import cn.blmdz.web.enums.OwnerType;
import cn.blmdz.web.enums.StatusType;
import cn.blmdz.web.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@PostConstruct
	public void init() {
		if (userDao.findByOwner(AuthType.ADMIN.name().toLowerCase()) == null) {
			Date date = new Date();
			QxxUser user = new QxxUser();
			user.setPresent(0L);
			user.setName(AuthType.ADMIN.name());
			user.setRole(AuthType.ADMIN);
			user.setType(OwnerType.ACCOUNT.value());
			user.setOwner(AuthType.ADMIN.name().toLowerCase());
			user.setPwd(EncryptUtil.encrypt(AuthType.ADMIN.name().toLowerCase()));
			user.setStatus(1);
			user.setCid(0L);
			user.setUid(0L);
			user.setCdate(date);
			user.setUdate(date);
			userDao.create(user);
		}
	}

	@Override
	public QxxUser login(String owner, String pwd) {
		QxxUser user = userDao.findByOwner(owner);
		if (user == null) throw new GlobalException("用户不存在");
		if (!EncryptUtil.match(pwd, user.getPwd())) throw new GlobalException("密码不正确");
		if (Objects.equal(StatusType.UN.value(), user.getStatus())) throw new GlobalException("用户被冻结");
		return user;
	}

	@Override
	public QxxUser findById(Long id) {
		return userDao.findById(id);
	}

	@Override
	public void updatePwd(Long id, String old, String snew) {
		
		QxxUser user = userDao.findById(id);
		if (!EncryptUtil.match(old, user.getPwd())) throw new GlobalException("密码不正确");
		if (Objects.equal(old, snew)) return;
		
		user.setUid(UserUtil.getCurrentUser().getId());
		user.setUdate(new Date());
		user.setPwd(EncryptUtil.encrypt(snew));
		userDao.update(user);
	}

	@Override
	public void resetPwd(Long id, String current) {
		QxxUser user = userDao.findById(UserUtil.getCurrentUser().getId());
		user.setUid(UserUtil.getCurrentUser().getId());
		user.setUdate(new Date());
		user.setPwd(EncryptUtil.encrypt(current));
		userDao.update(user);
	}
}
