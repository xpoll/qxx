package cn.blmdz.common.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

/**
 * 分页总类
 * @author lm
 * @date 2016年12月7日 下午11:44:19
 * @param <T>
 */
@Data
public class BasePage<S, T> {

	/** 每页多少个 */
	private int size;
	
	/** 第几页 */
	private int num;
	
	/** total */
	private long total;
	
	/** 返回数据 */
	private List<T> data;
	
	
	/** 搜索 */
	@JsonProperty(access = Access.WRITE_ONLY)
	private S mode;
	
	/** 排序 */
	@JsonProperty(access = Access.WRITE_ONLY)
	private String order;
}
