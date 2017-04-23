package cn.blmdz.book;

import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class G616 extends JFrame {

	private static final long serialVersionUID = 1L;

	public G616() {
		super("键盘事件处理的使用");
		Container c = getContentPane();
		JTextArea ta = new JTextArea(null, 6, 12);
		ta.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("失去焦点");
			}
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("获取焦点");
			}
		});
		ta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println("键盘事件：" + e.getKeyChar());
			}
		});
		c.add(ta);
	}
	
	public static void main(String[] args) {
		G616 app = new G616();
		app.setSize(260, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
