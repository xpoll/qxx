package cn.blmdz.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SOA RPC 调用封装对象，判断传输数据正确与否以及错误信息展示
 */
@ToString
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -4612301430071730437L;
    
    @Setter
    @Getter
    private boolean success;
    @Getter
    private T result;
    @Getter
    private String error;

    public void setResult(T result) {
        this.success = true;
        this.result = result;
    }

    public void setError(String error) {
        this.success = false;
        this.error = error;
    }

    public static <T> Response<T> ok(T data) {
        Response<T> resp = new Response<T>();
        resp.setResult(data);
        return resp;
    }

    public static <T> Response<T> fail(String error) {
        Response<T> resp = new Response<T>();
        resp.setError(error);
        return resp;
    }

    public static <T> Response<T> ok() {
        return ok(null);
    }

}
