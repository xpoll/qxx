package cn.blmdz.book;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class G602 {

	public static void main(String[] args) {
		JFrame app = new JFrame("对话框的使用");
		Container c = app.getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.LEFT));
		app.setSize(600, 300);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
		JDialog d = new JDialog(app, "对话框", false);
		d.setSize(200, 100);
		d.setVisible(true);
	}
}
