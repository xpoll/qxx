package cn.blmdz.search;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class CutWords implements Serializable {
	private static final long serialVersionUID = 4238688287928756380L;
	private List<Token> tokens = Collections.emptyList();
}