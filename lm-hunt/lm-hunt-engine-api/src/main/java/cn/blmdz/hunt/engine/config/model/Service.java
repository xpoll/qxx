package cn.blmdz.hunt.engine.config.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Service implements Serializable {
	private static final long serialVersionUID = 6022055077761299899L;
	private String app;
	private ServiceType type = ServiceType.NOT_SET;
	private boolean isTypeAutoDetected = false;
	private String uri;
	private String desc;

	public void setAutoDetectedType(ServiceType type) {
		this.isTypeAutoDetected = true;
		this.type = type;
	}


	public static enum ServiceType {
		NOT_SET, HTTP, DUBBO, SPRING, CANT_DETECTED;
	}
}