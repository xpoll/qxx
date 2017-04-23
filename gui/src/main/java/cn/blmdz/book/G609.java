package cn.blmdz.book;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;

public class G609 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public G609() {
		super("边界布局管理器的使用");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(new JButton("东"), BorderLayout.EAST);
		c.add(new JButton("西"), BorderLayout.WEST);
		c.add(new JButton("南"), BorderLayout.SOUTH);
		c.add(new JButton("北"), BorderLayout.NORTH);
		c.add(new JButton("中"), BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		G609 app = new G609();
		app.setSize(360, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
