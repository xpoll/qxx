package com.blmdz.mybatis.dao;

import org.springframework.stereotype.Repository;

import com.blmdz.mybatis.base.MyBatisDao;
import com.blmdz.mybatis.model.Student;
import com.google.common.collect.ImmutableMap;

@Repository
public class StudentDao extends MyBatisDao<Student> {

	public Student findById(Long id) {
		return getSqlSession().selectOne(sqlId("findById"), ImmutableMap.of("id", id));
	}
}
