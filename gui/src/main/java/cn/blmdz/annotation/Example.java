package cn.blmdz.annotation;

@MyAnnotation("TYPE")
public class Example {

	@MyAnnotation("METHOD name")
	public String name() {
		return "name";
	}
	
	@MyAnnotation("METHOD value")
	public String value() {
		return "value";
	}
	@MyAnnotation
	public String test(String a, Integer b) {
		
		return a + "," + b;
	}
}
