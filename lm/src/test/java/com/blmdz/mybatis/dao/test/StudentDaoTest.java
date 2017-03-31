package com.blmdz.mybatis.dao.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.blmdz.mybatis.dao.StudentDao;
import com.blmdz.mybatis.model.Student;

public class StudentDaoTest extends BaseDaoTest {

	@Autowired
	private StudentDao studentDao;
	
	private Student get(){
		Student s = new Student();
		s.setFlag(false);
		s.setName("name1");
		return s;
	}
	@Test
	public void create(){
		Student s = get();
		Boolean result = studentDao.create(s);
		Assert.assertTrue(result);
	}
}
