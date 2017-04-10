package cn.blmdz.hunt.engine.config.model;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class Mapping implements Serializable {
    private static final long serialVersionUID = 61453140428465964L;
    
    private String pattern;
    private Set<HttpMethod> methods;
    private String service;
    private String desc;
    private boolean csrfCheck = false;
}
