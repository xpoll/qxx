package cn.blmdz.source;

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