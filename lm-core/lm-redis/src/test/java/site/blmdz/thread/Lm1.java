package site.blmdz.thread;

public class Lm1 {

	public static void main(String[] args) {
		MyThread my = new MyThread();
		
		Thread a = new Thread(my, "A");
		Thread b = new Thread(my, "B");
		Thread c = new Thread(my, "C");
		Thread d = new Thread(my, "D");
		Thread e = new Thread(my, "E");
		a.start();
		b.start();
		c.start();
		d.start();
		e.start();
	}
}
