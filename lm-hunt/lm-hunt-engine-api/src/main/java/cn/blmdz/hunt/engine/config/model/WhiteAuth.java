package cn.blmdz.hunt.engine.config.model;

import java.io.Serializable;
import java.util.Set;
import java.util.regex.Pattern;

import lombok.Data;

@Data
public class WhiteAuth implements Serializable {
    private static final long serialVersionUID = 3696648541366561019L;
    
    private String pattern;
    private Pattern regexPattern;
    private Set<HttpMethod> methods;
}
