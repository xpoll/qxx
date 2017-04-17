package cn.blmdz.common.model;

import java.io.Serializable;
import java.util.List;

public interface BaseUser extends Serializable {

    Long getId();

    String getName();

    String getType();

    List<String> getRoles();
}
