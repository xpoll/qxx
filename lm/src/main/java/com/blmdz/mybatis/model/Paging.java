package com.blmdz.mybatis.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lm
 * 分页
 * @param <T>
 */
@Getter
@Setter
public class Paging<T> implements Serializable {
	private static final long serialVersionUID = 727775596841314096L;
	
	private Long total;
    private List<T> datas;
    

    public Paging(Long total, List<T> datas) {
        this.datas = datas;
        this.total = total;
    }

    public Boolean isEmpty() {
        return Objects.equals(0L, total) || null == datas || datas.isEmpty();
    }

    public static <T> Paging<T> empty() {
        return new Paging<>(0L, Collections.<T>emptyList());
    }

    public static <T> Paging<T> empty(Class<T> tClass) {
        return new Paging<>(0L, Collections.<T>emptyList());
    }
}
