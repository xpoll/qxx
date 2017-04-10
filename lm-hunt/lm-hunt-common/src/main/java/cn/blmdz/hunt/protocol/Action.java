package cn.blmdz.hunt.protocol;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public abstract class Action implements Serializable {
	private static final long serialVersionUID = 7537449026007075601L;
	@Getter
	@Setter
	private Object data;
}