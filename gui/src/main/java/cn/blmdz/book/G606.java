package cn.blmdz.book;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class G606 extends JFrame {
	private static final long serialVersionUID = 1L;


	public G606() {
		super("多行组件的使用");
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		String[] s = {"选项1", "选项2", "选项3"};
		JComboBox<String> cb = new JComboBox<String>(s);
		JList<String> lt = new JList<String>(s);
		JTextArea ta = new JTextArea("1\n2\n3\n4\n5\n6", 3, 9);
		JScrollPane sp = new JScrollPane(ta);
		c.add(cb);
		c.add(lt);
		c.add(sp);
	}
	
	
	public static void main(String[] args) {
		G606 app = new G606();
		app.setSize(260, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
	
}
