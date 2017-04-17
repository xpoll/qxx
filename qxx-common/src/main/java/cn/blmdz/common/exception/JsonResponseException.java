package cn.blmdz.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResponseException extends RuntimeException {
	private static final long serialVersionUID = -1147565039381879163L;

	private int status = 500;
	private String message = "unknown exception";

	public JsonResponseException() {
	}

	public JsonResponseException(String message) {
		this.message = message;
	}

	public JsonResponseException(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public JsonResponseException(int status, String message, Throwable cause) {
		super(message, cause);
		this.message = message;
		this.status = status;
	}

	public JsonResponseException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public JsonResponseException(int status, Throwable cause) {
		super(cause);
		this.message = cause.getMessage();
		this.status = status;
	}

	public JsonResponseException(Throwable cause) {
		super(cause);
		this.message = cause.getMessage();
	}
}
