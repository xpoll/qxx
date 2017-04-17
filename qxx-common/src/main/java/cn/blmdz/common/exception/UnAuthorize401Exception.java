package cn.blmdz.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorize401Exception extends RuntimeException {
    private static final long serialVersionUID = -5179634070581556579L;

    public UnAuthorize401Exception() {
    }

    public UnAuthorize401Exception(String message) {
        super(message);
    }

    public UnAuthorize401Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorize401Exception(Throwable cause) {
        super(cause);
    }
}
