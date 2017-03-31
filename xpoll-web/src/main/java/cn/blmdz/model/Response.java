package cn.blmdz.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Response<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Setter(AccessLevel.NONE)
	private String message;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Boolean success;
	
	@Setter(AccessLevel.NONE)
	private T data;

	public static Response<Boolean> ok() {
		return new Response<Boolean>(null, true, null);
	}
	
	public static Response<Boolean> faild() {
		return new Response<Boolean>(null, false, null);
	}
	
	public static<T> Response<T> build(T data) {
		return new Response<T>(null, true, data);
	}
	
	public Response<T> msg(String msg) {
		this.message = msg;
		return this;
	}
	
	public Boolean isSuccess() {
		return success;
	}

	public Response(String message, Boolean success, T data) {
		this.message = message;
		this.success = success;
		this.data = data;
	}

}
