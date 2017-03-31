package cn.blmdz.search;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Hits implements Serializable {
	private static final long serialVersionUID = -8033128358999935071L;
	private Long total;
	private Float max_score;
	private List<Hit> hits;
}