package cn.blmdz.source;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SString {
	public static void main(String[] args) {
		String a = "sdf";
		System.out.println(a.intern().equals(a));
		System.out.println(a.intern() == a);
		System.out.println(a.toString());
		System.out.println(new SString(1, "1").equals(new SString(1, "1")));
		System.out.println(new SString1().equals(new SString1()));
	}
	
	private int a;
	private String b;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + a;
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SString other = (SString) obj;
		if (a != other.a)
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}
	
}

class SString1 {
	
}
