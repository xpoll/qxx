package com.blmdz.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blmdz.mybatis.dao.StudentDao;
import com.blmdz.mybatis.model.Student;

@Service
public class StudentService {
	@Autowired
	private StudentDao studentDao;
	
	public Boolean Create(Student student) {
		return studentDao.create(student);
	}
}
