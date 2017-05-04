package cn.blmdz.book.g10;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class G1002 extends Thread {

	private String name;
	private Ticket tk;
	
	public G1002(String name, Ticket tk) {
		this.name = name;
		this.tk = tk;
	}
	
	@Override
	public void run() {
		while(tk.getNum() > 0 && tk.done(name)) {
		}
	}
	
	public static void main(String[] args) {
		Ticket tk = new Ticket(20);
		G1002 a = new G1002("张三", tk);
		G1002 b = new G1002("李四", tk);
		a.start();
		b.start();
		
	}

}

@Setter
@Getter
@AllArgsConstructor
class Ticket {
	private int num;
	
	public synchronized boolean done(String name) {
		if (num > 0) {
			System.out.println(name + "抢到了" + num + "号票");
			num--;
		} else {
			System.out.println("没票了");
			return false;
		}
		return true;
	}
}
