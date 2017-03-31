package site.blmdz.mobile.mode;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BaseModel {
	private BaseHead head = new BaseHead();
	@Setter
	private String sign;
}
