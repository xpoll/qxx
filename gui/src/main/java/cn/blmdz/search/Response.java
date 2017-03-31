package cn.blmdz.search;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class Response implements Serializable {
	private static final long serialVersionUID = 1920380772443937322L;
	private Integer took;
	private Boolean timed_out;
	private Shards _shards;
	private Hits hits;
	private Map<String, TermsAggregation> aggregations;
}