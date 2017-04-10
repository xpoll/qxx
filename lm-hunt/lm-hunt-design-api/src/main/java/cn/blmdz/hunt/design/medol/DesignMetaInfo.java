package cn.blmdz.hunt.design.medol;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DesignMetaInfo implements Serializable {

	private static final long serialVersionUID = 6641469394453976827L;
	private String redisUrl;
	private List<String> apps;
}
