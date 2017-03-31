package com.blmdz.mybatis.base;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.blmdz.mybatis.model.Paging;
import com.blmdz.mybatis.util.JsonMapper;
import com.google.common.collect.Maps;

/**
 * mybatis 基础dao类  mysql版本
 */
@SuppressWarnings("unchecked")
public abstract class MyBatisDao<T> {
    @Autowired
    protected SqlSessionTemplate sqlSession;

    protected static final String CREATE = "create";
    protected static final String CREATES = "creates";
    protected static final String DELETE = "delete";
    protected static final String DELETES = "deletes";
    protected static final String UPDATE = "update";
    protected static final String FIND_BY_ID = "findById";
    protected static final String FIND_BY_IDS = "findByIds";
    protected static final String LIST = "list";
    protected static final String COUNT = "count";
    protected static final String PAGING = "paging";
    public final String nameSpace;

    @SuppressWarnings("rawtypes")
	public MyBatisDao() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {//反射的实体对象
            nameSpace = ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
        } else {//不是反射的实体查找根class
            nameSpace = ((Class) ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
        }
    }

    public Boolean create(T t) {
        return sqlSession.insert(sqlId(CREATE), t) == 1;
    }

    public Integer creates(List<T> ts) {
        return sqlSession.insert(sqlId(CREATES), ts);
    }

	public Integer creates(T t0, T t1, T... tn) {
        return sqlSession.insert(sqlId(CREATES), Arrays.asList(t0, t1, tn));
    }

    public Boolean delete(Long id) {
        return sqlSession.delete(sqlId(DELETE), id) == 1;
    }

    public Integer deletes(List<Long> ids) {
        return sqlSession.delete(sqlId(DELETES), ids);
    }

    public Integer deletes(Long id0, Long id1, Long... idn) {
        return sqlSession.delete(sqlId(DELETES), Arrays.asList(id0, id1, idn));
    }

    public Boolean update(T t) {
        return sqlSession.update(sqlId(UPDATE), t) == 1;
    }

    public T findById(Integer id) {
        return findById(id);
    }

    public T findById(Long id) {
        return sqlSession.selectOne(sqlId(FIND_BY_ID), id);
    }

    public List<T> findByIds(List<Long> ids) {
        return (List<T>) (ids.isEmpty() ? Collections.emptyList() : sqlSession.selectList(sqlId(FIND_BY_IDS), ids));
    }

    public List<T> findByIds(Long id0, Long id1, Long... idn) {
        return sqlSession.selectList(sqlId(FIND_BY_IDS), Arrays.asList(id0, id1, idn));
    }

    public List<T> listAll() {
        return sqlSession.selectList(sqlId(LIST));
    }

    public List<T> list(T t) {
        return sqlSession.selectList(sqlId(LIST), t);
    }

    public List<T> list(Map<?, ?> criteria) {
        return sqlSession.selectList(sqlId(LIST), criteria);
    }

    public Paging<T> paging(Integer offset, Integer limit) {
        return paging(offset, limit, (new HashMap<String, Object>()));
    }

    public Paging<T> paging(Integer offset, Integer limit, T criteria) {
        HashMap<String, Integer> params = Maps.newHashMap();
        if (criteria != null) {
            Map<String, Integer> total = (Map<String, Integer>) JsonMapper.nonDefaultMapper().getMapper().convertValue(criteria, Map.class);
            params.putAll(total);
        }

        Long total1 = sqlSession.selectOne(sqlId(COUNT), criteria);
        if (total1 <= 0L) {
            return Paging.empty();
        } else {
            params.put("offset", offset);
            params.put("limit", limit);
            List<T> datas = sqlSession.selectList(sqlId(PAGING), params);
            return new Paging<T>(total1, datas);
        }
    }

    public Paging<T> paging(Integer offset, Integer limit, Map<String, Object> criteria) {
        if (criteria == null) {
            criteria = Maps.newHashMap();
        }

        Long total = sqlSession.selectOne(sqlId(COUNT), criteria);
        if (total <= 0) {
            return Paging.empty();
        } else {
            criteria.put("offset", offset);
            criteria.put("limit", limit);
            List<T> datas = sqlSession.selectList(sqlId(PAGING), criteria);
            return new Paging<T>(total, datas);
        }
    }

    public Paging<Object> paging(Map<String, Object> criteria) {
        if (criteria == null) {
            criteria = Maps.newHashMap();
        }

        Long total = (Long) sqlSession.selectOne(sqlId(COUNT), criteria);
        if (total <= 0L) {
            return new Paging<Object>(0L, Collections.emptyList());
        } else {
            List<Object> datas = sqlSession.selectList(sqlId(PAGING), criteria);
            return new Paging<Object>(total, datas);
        }
    }

    protected String sqlId(String id) {
        return nameSpace + "." + id;
    }

    protected SqlSessionTemplate getSqlSession() {
        return sqlSession;
    }
}
