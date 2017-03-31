package cn.blmdz.search;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TermsAggregation implements Serializable {
	private static final long serialVersionUID = -4914786763630433807L;
	private Long doc_count_error_upper_bound;
	private Long sum_other_doc_count;
	private List<Bucket> buckets;
}