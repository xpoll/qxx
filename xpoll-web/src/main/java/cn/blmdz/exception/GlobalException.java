package cn.blmdz.exception;

/**
 * 全局异常
 * @author yongzongyang
 */
public class GlobalException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    public GlobalException() {
        super();
    }
    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable throwable) {
        super(message, throwable);
    }
    public GlobalException(Throwable throwable) {
        super(throwable);
    }
}
