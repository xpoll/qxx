package cn.blmdz.hunt.client;

import java.io.Serializable;

import cn.blmdz.hunt.common.model.InnerCookie;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrapResp implements Serializable {
	private static final long serialVersionUID = 1025854443836742035L;

	private Object result;
	private InnerCookie cookie;
}