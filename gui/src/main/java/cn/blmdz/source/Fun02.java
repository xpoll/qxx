package cn.blmdz.source;


//1. static
//2. static 变量赋值
//3. 类{}-构造方法-父类{}-父类构造方法
public class Fun02 extends Fun021 {
	static {
		System.out.print("3");
	}

	Fun02() {
		System.out.print("2");
	}

	{
		System.out.print("4");
	}

	public static void main(String[] args) {
		new Fun02();
	}

}

class Fun021 {
	Fun021() {
		System.out.print("5");
	}

	{
		System.out.print("7");
	}
	
	static {
		System.out.print("6");
	}

}