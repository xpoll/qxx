package cn.blmdz.search;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Hit implements Serializable {
	private static final long serialVersionUID = 964771673077104982L;
	private String _index;
	private String _type;
	private String _id;
	private Float _score;
	private Map<String, Object> _source;
	private Map<String, List<String>> highlight;
}