package cn.blmdz.common.exception;

/**
 * SerializeException
 * @author lm
 * @date 2016年11月4日 下午10:51:54
 */
public class SerializeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SerializeException() {
	}
	
	public SerializeException(Throwable throwable) {
		super(throwable);
	}
	
	public SerializeException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
