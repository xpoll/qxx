package cn.blmdz.search;

import java.io.Serializable;

import lombok.Data;

@Data
public class Token implements Serializable {
	private static final long serialVersionUID = 4999895891300162608L;
	private String token;
	private Integer start_offset;
	private Integer end_offset;
	private String type;
	private Integer position;
}