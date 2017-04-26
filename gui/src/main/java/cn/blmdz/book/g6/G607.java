package cn.blmdz.book.g6;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class G607 extends JFrame {

	private static final long serialVersionUID = 1L;

	public G607() {
		super("面板和滚动条组件的使用");
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		JSlider s = new JSlider(JSlider.HORIZONTAL, 0, 26, 6);
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(100, 60));
		p.setBackground(Color.red);
		c.add(s);
		c.add(p);
	}
	
	public static void main(String[] args) {
		G607 app = new G607();
		app.setSize(360, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
