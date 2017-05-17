package cn.blmdz.web.service;

import cn.blmdz.web.entity.QxxUser;

/**
 * 用户
 * @author lm
 */
public interface UserService {
	
	/**
	 * 登陆
	 * @param owner
	 * @param pwd
	 * @return
	 */
	QxxUser login(String owner, String pwd);

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	QxxUser findById(Long id);

	/**
	 * 更改密码
	 * @param id
	 * @param old
	 * @param snew
	 */
	void updatePwd(Long id, String old, String current);
	
	/**
	 * 重置密码
	 * @param id
	 * @param current
	 */
	void resetPwd(Long id, String current);
}
