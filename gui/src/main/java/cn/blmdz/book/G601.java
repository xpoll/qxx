package cn.blmdz.book;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class G601 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public G601() {
		super("框架和标签的使用");
		Container c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.LEFT));
		String[] s = {"文本标签", "文字在左方", "文字在图片下方"};
		ImageIcon[] ic = {
				null,
				new ImageIcon("C:\\Users\\lm\\Pictures\\emoji\\+1.png"),
				new ImageIcon("C:\\Users\\lm\\Pictures\\emoji\\-1.png")
		};
		int[] ih = {0, JLabel.LEFT, JLabel.CENTER};
		int[] iv = {0, JLabel.CENTER, JLabel.BOTTOM};
		for (int i = 0; i < 3; i++) {
			JLabel label = new JLabel(s[i], ic[i], JLabel.LEFT);
			if (i > 0) {
				label.setHorizontalTextPosition(ih[i]);
				label.setVerticalTextPosition(iv[i]);
			}
			label.setToolTipText("第" + (i + 1) + "个标签");
			c.add(label);
		}
	}
	
	public static void main(String[] args) {
		G601 app = new G601();
		app.setSize(600, 300);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
