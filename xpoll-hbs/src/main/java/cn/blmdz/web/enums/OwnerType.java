package cn.blmdz.web.enums;

/**
 * <pre>
 *  1: 手机
 *  2: 邮箱
 *  3: 账号
 * </pre>
 */
public enum OwnerType {
	MOBILE(1), EMAIL(2), ACCOUNT(3);

	private final int value;

	OwnerType(Integer value) {
		this.value = value;
	}
	public int value(){
		return value;
	}
}
