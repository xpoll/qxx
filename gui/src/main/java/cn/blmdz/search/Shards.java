package cn.blmdz.search;

import java.io.Serializable;

import lombok.Data;

@Data
public class Shards implements Serializable {
	private static final long serialVersionUID = 8970518308372594928L;
	private Integer total;
	private Integer successful;
	private Integer failed;
}