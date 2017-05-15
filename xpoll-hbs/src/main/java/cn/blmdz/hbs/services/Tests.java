package cn.blmdz.hbs.services;

import org.springframework.stereotype.Component;

import cn.blmdz.hbs.util.Export;
import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class Tests {

	@Export
	public Student test() {
		return new Student(10L, "小明");
	}
}

@Data
@AllArgsConstructor
class Student {
	private Long id;
	private String name;
}
