package cn.blmdz.book.g6;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class G608 extends JFrame {

	private static final long serialVersionUID = 1L;

	public G608() {
		super("流布局管理器的使用");
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		JButton b1 = new JButton("确定");
		JButton b2 = new JButton("取消");
		JButton b3 = new JButton("关闭");
		c.add(b1);
		c.add(b2);
		c.add(b3);
	}
	
	public static void main(String[] args) {
		G608 app = new G608();
		app.setSize(360, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
