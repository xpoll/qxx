package cn.blmdz.hbs.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.blmdz.hbs.util.Export;
import lombok.AllArgsConstructor;
import lombok.Data;

@Component
public class Tests {

	@Export
	public Student testa() {
		return new Student(10L, "小明");
	}
	@Export("name")
	public Student testb(String name) {
		return new Student(10L, name);
	}
	@Export("a")
	public Student testc(String name) {
		return new Student(10L, name);
	}
	@Export({"id", "name"})
	public Student testc(Long id, String name) {
		return new Student(id, name);
	}
	@Export
	public List<Map<String, String>> bbb() {
        List<Map<String, String>> list = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("a", "Lily");
        map.put("age", "23");
        list.add(map);
        map = Maps.newHashMap();
        map.put("a", "Tom");
        map.put("age", "25");
        list.add(map);
        return list;
	}
}

@Data
@AllArgsConstructor
class Student {
	private Long id;
	private String name;
}
