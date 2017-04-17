package cn.blmdz.common.model;

import java.util.List;

import cn.blmdz.common.model.BaseUser;
import lombok.Data;

@Data
public class User implements BaseUser {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String type;
	private Long presentUserId;
	private List<String> roles;
	
}
