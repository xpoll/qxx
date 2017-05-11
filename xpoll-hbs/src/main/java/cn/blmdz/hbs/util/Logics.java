package cn.blmdz.hbs.util;

import com.github.jknack.handlebars.Handlebars.Utils;

public class Logics {
	public static boolean or(Object a, Object b) {
		boolean bA = !Utils.isEmpty(a);
		boolean bB = !Utils.isEmpty(b);
		return bA || bB;
	}

	public static boolean and(Object a, Object b) {
		boolean bA = !Utils.isEmpty(a);
		boolean bB = !Utils.isEmpty(b);
		return bA && bB;
	}
}
