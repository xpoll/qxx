package cn.blmdz.hunt.design.medol;

import java.io.Serializable;

import lombok.Data;

@Data
public class Site implements Serializable {
	private static final long serialVersionUID = -1707245808544541752L;
	private Long id;
	private String app;
	private Long userId;
	private String name;
	private String domain;
	private String layout;
	private String layoutName;
	private String index;
}
