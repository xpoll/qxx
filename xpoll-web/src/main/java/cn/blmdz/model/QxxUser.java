package cn.blmdz.model;

import java.util.Date;

import lombok.Data;

@Data
public class QxxUser {
	private Long id;
	private Long present;
	private String name;
	private String auth;
	private Integer type;
	private String owner;
	
	private Integer status;
	private Date cdate;
	private Date udate;
}
