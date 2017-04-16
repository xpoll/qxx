package cn.blmdz.aide.file.exception;

public class FileInfoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FileInfoException() {
	}

	public FileInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileInfoException(String message) {
		super(message);
	}
}
