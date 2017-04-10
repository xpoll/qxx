package cn.blmdz.hunt.engine.config.model;

import java.io.Serializable;
import java.util.Set;
import java.util.regex.Pattern;

import lombok.Data;

@Data
public class ProtectedAuth implements Serializable {
    private static final long serialVersionUID = 6649344563529265570L;
    
    private String pattern;
    private Pattern regexPattern;
    private Set<String> types;
    private Set<String> roles;
}
