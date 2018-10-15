package cn.blmdz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

	
	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date d2 = sdf2.parse("2017-06-06");
		System.out.println(sdf1.format(d2));
		Calendar.getInstance();
		Double a = 0.1;
		Double b = 0.2;
		System.out.println(a + b);
		
//		for (int i = 0; i < 10000000; i++) {
//			System.out.print(i);
//		}
		
		System.out.println("2012-02-65".replace("-", ""));
	}
}
