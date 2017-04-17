package cn.blmdz.redis;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Date date;
	
	public Student(String name, Date date) {
		this.name = name;
		this.date = date;
	}
	
}
