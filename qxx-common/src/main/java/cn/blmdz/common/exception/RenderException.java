package cn.blmdz.common.exception;

public class RenderException extends RuntimeException {
    private static final long serialVersionUID = 3581303840451244869L;

    public RenderException() {
    }

    public RenderException(String message) {
        super(message);
    }

    public RenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public RenderException(Throwable cause) {
        super(cause);
    }
}
