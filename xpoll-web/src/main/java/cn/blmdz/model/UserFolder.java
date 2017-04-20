package cn.blmdz.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserFolder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long createBy;
	private String group;
	private Long pid;
	private Integer level;
	private Boolean hasChildren;
	private String folder;
	private Date createdAt;
	private Date updatedAt;
}
