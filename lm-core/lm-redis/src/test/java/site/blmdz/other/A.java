package site.blmdz.other;

import java.util.HashMap;
import java.util.Map;

public class A {
	public static void main(String[] args) {
		//子类的输入参数宽于父类或者等于父类的输入参数，那么你写的方法是不会被调用的
		//等于父类的输入参数就是重写，是可以被调用的 ？？？ 18页
		A2 a = new A2();
		a.test(new HashMap());//A1
		A4 b = new A4();
		b.test(new HashMap());//A4
	}
}
class A1{
	void test(HashMap map){
		System.out.println("A1");
	}
}
class A2 extends A1{
	void test(Map map){
		System.out.println("A2");
	}
	@Override
	void test(HashMap map){
		System.out.println("A2 - A1");
	}
}
class A3{
	void test(Map map){
		System.out.println("A3");
	}
}
class A4 extends A3{
	void test(HashMap map){
		System.out.println("A4");
	}
	@Override
	void test(Map map){
		System.out.println("A4 - A3");
	}
}

