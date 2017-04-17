package cn.blmdz.common.exception;

public class SessionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SessionException() {
	}
	
	public SessionException(String msg) {
		super(msg);
	}
	
	public SessionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
	
	public SessionException(Throwable throwable) {
		super(throwable);
	}
}
