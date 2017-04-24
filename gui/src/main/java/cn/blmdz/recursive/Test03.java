package cn.blmdz.recursive;

/**
 * 计算斐波那契数
 */
public class Test03 {

	public static void main(String[] args) {
		int x = 10;
		System.out.println(qs(x));
	}
	private static long qs(long x) {
		if (x == 0) return 0;
		if (x == 1) return 1;
		return (qs(x - 1) + qs(x - 2));
	}
}
