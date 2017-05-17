package cn.blmdz.web.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long createBy;
	private Integer fileType;
	private String group;
	private Long folderId;
	private String name;
	private String path;
	private Integer size;
	private String extra;
	private Date createdAt;
	private Date updatedAt;
}
