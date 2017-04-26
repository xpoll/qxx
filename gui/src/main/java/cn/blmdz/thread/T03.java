package cn.blmdz.thread;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class T03 extends JApplet {

	private static final long serialVersionUID = 1L;
	
	public T03() {
		add(new JLabel("T03"));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame app = new JFrame("T03T03T03T03T03");
				app.add(new T03());
				app.setSize(200, 200);
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				app.setLocationRelativeTo(null);
				app.setVisible(true);
			}
		});
	}

}
