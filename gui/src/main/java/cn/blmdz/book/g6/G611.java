package cn.blmdz.book.g6;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class G611 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public G611() {
		super("盒子布局管理器的使用");
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.X_AXIS));
		
		for (int i = 0; i < 6; i++) {
			String s = "按钮" + (i + 1);
			JButton b = new JButton(s);
			c.add(b);
		}
	}
	
	public static void main(String[] args) {
		G611 app = new G611();
		app.setSize(360, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}



}
