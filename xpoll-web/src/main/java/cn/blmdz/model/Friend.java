package cn.blmdz.model;

import java.util.Date;

import lombok.Data;

@Data
public class Friend {

	private Long id;
	private Long key;
	
	private Integer status;
	private Date cdate;
	private Date udate;
}
