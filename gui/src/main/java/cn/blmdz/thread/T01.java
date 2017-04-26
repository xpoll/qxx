package cn.blmdz.thread;

public class T01 {

	public static void main(String[] args) {
		Runnable a = new PrCh('a', 100);
		Runnable b = new PrCh('b', 100);
		Runnable c = new PrNum(100);
		
		new Thread(a).start();
		new Thread(b).start();
		new Thread(c).start();
//		new Thread(c).interrupt();
		
	}
}

class PrCh implements Runnable {
	private char c;
	private int m;
	public PrCh(char c, int m) {
		this.c = c;
		this.m = m;
	}
	@Override
	public void run() {
		for (int i = 0; i < m; i++) {
			System.out.print(c + " ");
		}
	}
	
}

class PrNum implements Runnable {
	private int m;
	public PrNum(int m) {
		this.m = m;
	}
	@Override
	public void run() {
		for (int i = 0; i < m; i++) {
			System.out.print(i + " ");
			Thread.yield();//为其他线程临时让出cpu时间
//			Thread.sleep(1);
		}
	}
	
}