package cn.blmdz.book.g6;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


public class G624 implements TreeModelListener {
	
	JLabel label;
	String nodeName;

	public G624() {
		JFrame app = new JFrame("TreeModelEvent事件的使用");
		Container c = app.getContentPane();
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
		tree.setEditable(true);
		tree.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent e) {
				try {
					JTree tree = (JTree) e.getSource();
					int row = tree.getRowForLocation(e.getX(), e.getY());
					TreePath treePath = tree.getPathForRow(row);
					TreeNode treeNode = (TreeNode) treePath.getLastPathComponent();
					nodeName = treeNode.toString();
				} catch (NullPointerException ex) {
					System.out.println("null 1");
				}
		    }
		});
		tree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					JTree tree = (JTree) e.getSource();
					TreePath treePath = tree.getSelectionPath();
					TreeNode treeNode = (TreeNode) treePath.getLastPathComponent();
					nodeName = treeNode.toString();
				} catch (NullPointerException ex) {
					System.out.println("null 1");
				}
			}
		});
		treeModel = (DefaultTreeModel) tree.getModel();
		treeModel.addTreeModelListener(this);
		
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(tree);
		label = new JLabel();
		c.add(jScrollPane, BorderLayout.CENTER);
		c.add(label, BorderLayout.SOUTH);
		
		app.pack();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
	
	public static void main(String[] args) {
		new G624();
	}

	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		try {
			TreePath treePath = e.getTreePath();
			System.out.println(treePath);
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
			int[] index = e.getChildIndices();
			node = (DefaultMutableTreeNode) node.getChildAt(index[0]);
			if (!node.getUserObject().toString().equals(nodeName)) {
				label.setText("[" + nodeName + "] 更改数据为：[" + node.getUserObject() + "]");
			} else {
				label.setText("未修改！");
			}
		} catch (NullPointerException ex) {
			System.out.println("null 2");
		}
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
	}
	
}
