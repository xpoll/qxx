package cn.blmdz.recursive;

/**
 * 阶乘
 */
public class Test02 {

	public static void main(String[] args) {
		int x = 5;
		System.out.println(jx(x));
	}
	public static long jx(int x) {
		return (x == 1) ? 1 : x * jx(x - 1);
	}
}
