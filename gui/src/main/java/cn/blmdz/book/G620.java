package cn.blmdz.book;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;

public class G620 extends JFrame {

	private static final long serialVersionUID = 1L;

	public G620() {
		super("无参数构造方法的使用");
		Container c = getContentPane();
		JTree tree = new JTree();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tree);
		c.add(scrollPane);
	}
	
	public static void main(String[] args) {
		G620 app = new G620();
		app.pack();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
