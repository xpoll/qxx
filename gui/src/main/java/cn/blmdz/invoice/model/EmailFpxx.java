package cn.blmdz.invoice.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

@Data
public class EmailFpxx {

	@XStreamAlias("COMMON_NODES")
	private EmailCommonNodes emailCommonNodes;
}
