package site.blmdz.shape;

import lombok.Data;

public class Test {
	public static Ab t(Ab ab){
		return new Ab("222");
	}
	
	public static void main(String[] args) {
		Ab ab = new Ab("1111");
		System.out.println(t(ab).getName());
		System.out.println(ab.getName());
	}
}

@Data
class Ab{
	private String name;

	public Ab(String name) {
		this.name = name;
	}
	
}
