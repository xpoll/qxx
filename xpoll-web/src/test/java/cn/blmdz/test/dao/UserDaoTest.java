package cn.blmdz.test.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import cn.blmdz.common.util.EncryptUtil;
import cn.blmdz.dao.UserDao;
import cn.blmdz.entity.QxxUser;
import cn.blmdz.enums.AuthType;
import cn.blmdz.enums.OwnerType;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDaoTest {

	@Autowired
	private UserDao userDao;

	@Test
	public void create() {
		Long id = userDao.create(entity());
		System.out.println(id);
	}

	@Test
	public void findIdByOwner() {
		QxxUser user = userDao.findByOwner("blmdzz.");
		System.out.println(user);
	}

	@Test
	public void findById() {
		QxxUser user = userDao.findById(2L);
		System.out.println(user.toString());
	}
	
	@Test
	public void findByIds() {
		List<Long> ids = Lists.newArrayList();
		ids.add(1L);
		ids.add(2L);
		List<QxxUser> users = userDao.findByIds(ids);
		for (QxxUser user : users) {
			System.out.println(user.toString());
		}
	}
	
	public QxxUser entity() {
		Date date = new Date();
		QxxUser user = new QxxUser();
		user.setPresent(0L);
		user.setName("小号");
		user.setRole(AuthType.ADMIN);
		user.setType(OwnerType.ACCOUNT.value());
		user.setOwner(AuthType.ADMIN.name().toLowerCase());
		user.setPwd(EncryptUtil.encrypt(AuthType.ADMIN.name().toLowerCase()));
		user.setStatus(1);
		user.setCid(0L);
		user.setUid(0L);
		user.setCdate(date);
		user.setUdate(date);
		return user;
	}
}
