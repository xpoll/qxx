package cn.blmdz.io;

import java.text.DecimalFormat;

//进制转换
public class I01 {
	public static void main(String[] args) {
//		System.out.println(tran16To10("400"));
//		System.out.println(tran10To16(1024));
		System.out.println(String.format("%s%s", "sdf","sdf"));
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.println(df.format(14514515.2115));
	}
	
	//10 --> 16
	public static String tran10To16(int num) {
		StringBuffer sb = new StringBuffer();
		while (num != 0) {
			int n = num % 16;
			num = num / 16;
			sb.insert(0, (n <= 9 && n >= 0) ? (char) (n + '0') : (char) (n - 10 + 'A'));
		}
		return sb.toString();
	}
	//16 --> 10
	public static int tran16To10(String num) {
		char[] c = num.toUpperCase().toCharArray();
		int m = 0;
		for (int i = c.length - 1; i >= 0; i--) {
			m += ((c[i] >= 'A') ? (c[i]-'A' + 10) : (c[i]-'0')) *  Math.pow(16, (c.length - i - 1));
		}
		return m;
	}

	public static int tran8To10(int num) {
		return 0;
	}
	public static int tran10To8(int num) {
		return 0;
	}
}
