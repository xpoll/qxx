package cn.blmdz.concurrent;

public class ReOrderTest {

	private int i = 0;
	private int j = 1;
	private boolean b = false;

	public static void main(String[] args) {
		for (int m = 0; m<10; m++) {
			ReOrderTest t = new ReOrderTest();
			new C(true, t).start();
			new C(false, t).start();
		}
	}
	
	public void write() {
		b = true;
		i = 2;
	}
	
	public void read() {
		if (b) {
			j = i * 3;
		}
		System.out.println(j);
	}
}

class C extends Thread {
	private boolean flag;
	private ReOrderTest t;
	
	public C(boolean flag, ReOrderTest t) {
		this.flag = flag;
		this.t = t;
	}
	
	@Override
	public void run() {
		try {Thread.sleep(30);}catch (InterruptedException e) {}//为了效果更直观 
		if (flag) {
			t.write();
		} else {
			t.read();
		}
	}
}