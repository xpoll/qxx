package cn.blmdz.book.g10;

import java.awt.BorderLayout;
import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class G1001 extends JFrame {

	private static final long serialVersionUID = 1L;

	public G1001() {
		super("时间");
		Container c = getContentPane();
		JLabel label = new JLabel("时钟");
		label.setHorizontalAlignment(JLabel.CENTER);
		c.setLayout(new BorderLayout());
		c.add(label, BorderLayout.CENTER);
		this.setSize(160, 80);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		new ThreadTimeG(label).start();
	}
	
	class ThreadTimeG extends Thread {
		private JLabel label;
		
		public ThreadTimeG(JLabel label) {
			this.label = label;
		}
		@Override
		public void run() {
			while(true) {
				label.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new G1001();
	}

}
