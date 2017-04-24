package cn.blmdz.book;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class G604 extends JFrame {

	private static final long serialVersionUID = 1L;

	public G604() {
		super("文本编辑器的使用");
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		JTextField[] t = {
				new JTextField("用户名：", 6),
				new JTextField("请输入用户名", 16),
				new JTextField("密码：", 6),
				new JPasswordField("xxxxxx：", 16)
		};
		t[0].setEditable(false);
		t[2].setEditable(false);
		for (int i = 0; i < t.length; i++) {
			c.add(t[i]);
		}
	}
	
	public static void main(String[] args) {
		G604 app = new G604();
		app.setSize(300, 200);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
