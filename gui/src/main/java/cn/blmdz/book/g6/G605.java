package cn.blmdz.book.g6;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class G605 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public G605() {
		super("按钮例程");
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		ImageIcon[] ii = {
				new ImageIcon("C:\\Users\\lm\\Pictures\\emoji\\arrow_backward.png"),
				new ImageIcon("C:\\Users\\lm\\Pictures\\emoji\\arrow_forward.png")
		};
		JButton[] b = {
				new JButton("左", ii[0]),
				new JButton("中间"),
				new JButton("右", ii[1])
		};
		for (int i = 0; i < b.length; i++) {
			c.add(b[i]);
		}
		JCheckBox[] cb = {
				new JCheckBox("左"),
				new JCheckBox("右")
		};
		for (int i = 0; i < cb.length; i++) {
			c.add(cb[i]);
			cb[i].setSelected(true);
		}
		JRadioButton[] rb = {
				new JRadioButton("左"),
				new JRadioButton("右")
		};
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < rb.length; i++) {
			c.add(rb[i]);
			bg.add(rb[i]);
		}
		rb[0].setSelected(true);
		rb[1].setSelected(false);
	}
	
	public static void main(String[] args) {
		G605 app = new G605();
		app.setSize(350, 260);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
