package cn.blmdz.hunt.design.medol;


import java.io.Serializable;

import lombok.Data;

@Data
public class Page implements Serializable {

	private static final long serialVersionUID = 2168969938795932716L;
	
	private Long id;
	private Long siteId;
	private String app;
	private Type type;
	private String path;
	private String link;
	private String name;
	private String title;
	private String keywords;
	private String description;
	private boolean immutable;
	
	public static enum Type {
		PAGE, LINK;
	}
}
