package cn.blmdz.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class Server500Exception extends RuntimeException {
    private static final long serialVersionUID = -6799208467426832752L;

    public Server500Exception() {
    }

    public Server500Exception(String message) {
        super(message);
    }

    public Server500Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Server500Exception(Throwable cause) {
        super(cause);
    }
}
