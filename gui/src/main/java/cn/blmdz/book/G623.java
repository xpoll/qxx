package cn.blmdz.book;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class G623 extends JFrame {

	private static final long serialVersionUID = 1L;

	public G623() {
		super("利用TreeModel建立树");
		Container c = getContentPane();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("资源管理器");
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("我的公文包");
		DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("我的电脑");
		DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("收藏夹");
		
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		
		treeModel.insertNodeInto(node1, root, root.getChildCount());
		treeModel.insertNodeInto(node2, root, root.getChildCount());
		treeModel.insertNodeInto(node3, root, root.getChildCount());

		treeModel.insertNodeInto(new DefaultMutableTreeNode("公司文件"), node1, node1.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("个人文件"), node1, node1.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("私人文件"), node1, node1.getChildCount());

		treeModel.insertNodeInto(new DefaultMutableTreeNode("本地磁盘(C:)"), node2, node2.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("本地磁盘(D:)"), node2, node2.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("本地磁盘(E:)"), node2, node2.getChildCount());

		treeModel.insertNodeInto(new DefaultMutableTreeNode("网上聊天"), node3, node3.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("网络新闻"), node3, node3.getChildCount());
		treeModel.insertNodeInto(new DefaultMutableTreeNode("网络书店"), node3, node3.getChildCount());
		
		JTree tree = new JTree(treeModel);
		tree.putClientProperty("JTree.lineStyle", "Horizontal");
		
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(tree);
		c.add(jScrollPane);
	}
	
	public static void main(String[] args) {
		G623 app = new G623();
		app.pack();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
