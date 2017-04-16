package cn.blmdz.aide.file.exception;

public class ImageException extends FileException {
	private static final long serialVersionUID = 1L;

	public ImageException() {
	}

	public ImageException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageException(String message) {
		super(message);
	}

	public ImageException(Throwable cause) {
		super(cause);
	}

	public ImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
