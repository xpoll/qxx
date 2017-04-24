package cn.blmdz.book;

import java.awt.Container;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;

public class G621 extends JFrame {

	private static final long serialVersionUID = 1L;

	public G621() {
		super("利用HashTable建立树");
		Container c = getContentPane();
		String[] s1 = {"本地磁盘(C:)", "本地磁盘(D:)", "本地磁盘(E:)"};
		String[] s2 = {"网上聊天", "网络新闻", "网络书店"};
		String[] s3 = {"公司文件", "个人信件", "私人文件"};
		Hashtable<String, Object> h1 = new Hashtable<>();
		Hashtable<String, String[]> h2 = new Hashtable<>();
		h1.put("我的电脑", s1);
		h1.put("收藏夹", h2);
		h2.put("网站列表", s2);
		h1.put("我的公文包", s3);
		JTree tree = new JTree(h1);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tree);
		c.add(scrollPane);
	}
	
	public static void main(String[] args) {
		G621 app = new G621();
		app.pack();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
