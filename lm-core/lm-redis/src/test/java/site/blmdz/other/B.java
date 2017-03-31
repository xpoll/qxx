package site.blmdz.other;

public class B {

	public static void test(Long a){
		System.out.println("Long");
	}
	public static void test(long a){
		System.out.println("long");
	}
	public static void main(String[] args) {
		test(Long.valueOf(1L));
		test(1L);
	}
}
