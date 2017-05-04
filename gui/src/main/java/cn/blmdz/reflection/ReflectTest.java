package cn.blmdz.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import lombok.Data;

public class ReflectTest {
	
	public static void main(String[] args) throws Exception {
		// reflect
		System.out.println(Double.class.isAnnotation());
		System.out.println(Double.class.isInterface());
		System.out.println(Double.class.isEnum());
		
		for(Field f : Double.class.getFields()) {
			System.out.println(f.getType().getName() + ": " + f.getName() + ": " + f.get(f.getName()));
		}
		
		for(Constructor<?> constructor : Double.class.getConstructors()) {
			for(Class<?> clazz : constructor.getParameterTypes()) {
				System.out.print(clazz.getSimpleName() + " ");
			}
			System.out.println();
		}
		BBB bbb = new BBB();
		bbb.setName("sdfsdfsdfsf");
		Method m = BBB.class.getMethod("a", String.class);
		
		System.out.println(m.invoke(bbb, "bffffff"));
		System.out.println(BBB.class.getMethod("getName").invoke(bbb));
		System.out.println(BBB.class.getMethod("b").invoke(bbb));
		
		// clone 
		BBB ccc = bbb.clone();
		ccc.setAge(100);
		System.out.println(bbb.toString());
		System.out.println(ccc.toString());
		
	}
}

@Data
class BBB implements Cloneable {
	private String name;
	private int age;

	public String a(String aa) {
		return aa;
	}
	public void b() {
	}
	
	@Override
	public BBB clone() throws CloneNotSupportedException {
		return (BBB)super.clone();
	}
	
}