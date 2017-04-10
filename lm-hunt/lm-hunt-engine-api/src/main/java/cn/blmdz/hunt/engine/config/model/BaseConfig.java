package cn.blmdz.hunt.engine.config.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public abstract class BaseConfig implements Serializable {
	private static final long serialVersionUID = -3815877052270311523L;

	private String app;
	private String sign;
	private Date loadedAt;
	private String location;
	private List<String> imported = Lists.newArrayList();

	public abstract void merge(BaseConfig config);
}
