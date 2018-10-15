//package cn.blmdz;
//
//import java.lang.reflect.InvocationTargetException;
//
//import org.apache.commons.beanutils.PropertyUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.cglib.beans.BeanCopier;
//
//public class CA {
//
//	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
////		CC cc = new CC(2L, "2name");
////		System.out.println(cc.toString());
////		test1(cc);
////		System.out.println(cc.toString());
////		test2(cc);
////		System.out.println(cc.toString());
//		CC cc = new CC(2L, "2name");
//		CC b = new CC();
//		
//		int max = 10000;
//		
//		Long s1 = System.currentTimeMillis();
//		for (int i = 0; i < max; i++) {
//			BeanUtils.copyProperties(cc, b);
//		}
//		Long s2 = System.currentTimeMillis();
//		System.out.println("spring.BeanUtils: " + (s2-s1));
//		
//		s1 = System.currentTimeMillis();
//		for (int i = 0; i < max; i++) {
//			org.apache.commons.beanutils.BeanUtils.copyProperties(cc, b);
//		}
//		s2 = System.currentTimeMillis();
//		System.out.println("apache.BeanUtils: " + (s2-s1));
//		
//		// 类需要是public
//		s1 = System.currentTimeMillis();
//		for (int i = 0; i < max; i++) {
//			PropertyUtils.copyProperties(cc, b);
//		}
//		s2 = System.currentTimeMillis();
//		System.out.println("apache.PropertyUtils: " + (s2-s1));
//		
//		s1 = System.currentTimeMillis();
//		BeanCopier copy = BeanCopier.create(CC.class, CC.class, false);
//		for (int i = 0; i < max; i++) {
//			copy.copy(cc, b, null);
//		}
//		s2 = System.currentTimeMillis();
//		System.out.println("cglib.BeanCopier.false: " + (s2-s1));
//	}
//	
//	public static boolean test1(CC cc) {
//		CC c = new CC(10L, "test1");
//		cc = c;
//		return true;
//	}
//	
//	public static boolean test2(CC cc) {
//		cc.setName("test2");
//		return true;
//	}
//}
////class CC {
////	private Long id;
////	private String name;
////	
////	public Long getId() {
////		return id;
////	}
////	public void setId(Long id) {
////		this.id = id;
////	}
////	public String getName() {
////		return name;
////	}
////	public void setName(String name) {
////		this.name = name;
////	}
////	public CC() {
////	}
////	public CC(Long id, String name) {
////		super();
////		this.id = id;
////		this.name = name;
////	}
////	@Override
////	public String toString() {
////		return "CC [id=" + id + ", name=" + name + "]";
////	}
////}
