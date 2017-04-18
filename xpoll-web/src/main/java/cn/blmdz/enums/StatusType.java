package cn.blmdz.enums;

/**
 * <pre>
 *  1: 可用
 *  0: 冻结
 * -1: 删除
 * </pre>
 */
public enum StatusType {
	OK(1), DEL(-1), UN(0);

	private final int value;

	StatusType(Integer value) {
		this.value = value;
	}
	public int value(){
		return value;
	}
}
