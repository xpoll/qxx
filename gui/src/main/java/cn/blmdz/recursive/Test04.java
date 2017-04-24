package cn.blmdz.recursive;

/**
 * 选择排序
 */
public class Test04 {

	public static void main(String[] args) {
		Integer[] intarr = {210, 102, 201, 101, 101};
		min(intarr, 0);
		for (Integer integer : intarr) {
			System.out.println(integer);
		}
		
	}
	public static void min(Integer[] intarr, int index) {
		
		int num = intarr[index];
		
		for (int i = index + 1; i < intarr.length; i++) {
			if (num > intarr[i]) {
				intarr[index] = intarr[i];
				intarr[i] = num;
				num = intarr[index];
			}
		}
		if (intarr.length - index > 1) {
			min(intarr, ++index);
		}
	}
}
