package cn.blmdz.hunt.engine.utils;

public class Numbers {
	public static int compare(Object n1, Object n2) {
		if (n1 instanceof Number && n2 instanceof Number) {
			double d1 = ((Number) n1).doubleValue();
			double d2 = ((Number) n2).doubleValue();
			return d1 < d2 ? -1 : (d1 == d2 ? 0 : 1);
		} else {
			throw new IllegalArgumentException("not a number");
		}
	}
}
