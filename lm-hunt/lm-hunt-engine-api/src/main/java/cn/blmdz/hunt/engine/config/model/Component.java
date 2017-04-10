package cn.blmdz.hunt.engine.config.model;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class Component implements Serializable {
    private static final long serialVersionUID = -1877549770928584021L;
    
    private String path;
    private String category = "ADMIN";
    private String name;
    private String desc;
    private String service;
    private Map<String, String> services;
}
