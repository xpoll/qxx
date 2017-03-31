package site.blmdz.mobile.mode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseBody {
	private boolean success = false;
	private String errorCode;
	private String errorMsg;
}
