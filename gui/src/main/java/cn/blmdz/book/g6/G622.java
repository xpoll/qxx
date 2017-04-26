package cn.blmdz.book.g6;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class G622 extends JFrame {

	private static final long serialVersionUID = 1L;

	public G622() {
		super("利用DefaultMutableTreeNode建立树");
		Container c = getContentPane();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("资源管理器");
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("我的公文包");
		DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("我的电脑");
		DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("收藏夹");
		root.add(node1);
		root.add(node2);
		root.add(node3);
		
		node1.add(new DefaultMutableTreeNode("公司文件"));
		node1.add(new DefaultMutableTreeNode("个人文件"));
		node1.add(new DefaultMutableTreeNode("私人文件"));
		
		node2.add(new DefaultMutableTreeNode("本地磁盘(C:)"));
		node2.add(new DefaultMutableTreeNode("本地磁盘(D:)"));
		node2.add(new DefaultMutableTreeNode("本地磁盘(E:)"));
		
		node3.add(new DefaultMutableTreeNode("网上聊天"));
		node3.add(new DefaultMutableTreeNode("网络新闻"));
		node3.add(new DefaultMutableTreeNode("网络书店"));
		
		JTree tree = new JTree(root);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(tree);
		c.add(jScrollPane);
	}
	
	public static void main(String[] args) {
		G622 app = new G622();
		app.pack();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
