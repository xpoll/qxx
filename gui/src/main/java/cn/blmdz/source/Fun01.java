package cn.blmdz.source;

public class Fun01 {

	public static void main(String[] args) {
		staticFunction();
	}
	
	static Fun01 st;
	int a = 110;
	static int b = 112;
	
	static {
		st = new Fun01();
		System.out.println("1");
	}
	
	{
		System.out.println("2");
	}

	Fun01() {
		System.out.println("3");
		System.out.println("a=" + a + ",b=" + b);
	}

	public static void staticFunction() {
		System.out.println("4");
	}
}
