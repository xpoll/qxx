package cn.blmdz.common.exception;

public class UserNotLoginException extends RuntimeException {
	private static final long serialVersionUID = 3138430951802077345L;

	public UserNotLoginException() {
	}

	public UserNotLoginException(String message) {
		super(message);
	}

	public UserNotLoginException(Throwable cause) {
		super(cause);
	}

	public UserNotLoginException(String message, Throwable cause) {
		super(message, cause);
	}
}