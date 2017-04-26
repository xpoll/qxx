package cn.blmdz.book.g6;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class G610 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public G610() {
		super("网格布局管理器的使用");
		Container c = getContentPane();
		c.setLayout(new GridLayout(2, 3));
		
		for (int i = 0; i < 6; i++) {
			String s = "按钮" + (i + 1);
			JButton b = new JButton(s);
			c.add(b);
		}
	}
	
	public static void main(String[] args) {
		G610 app = new G610();
		app.setSize(360, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}



}
