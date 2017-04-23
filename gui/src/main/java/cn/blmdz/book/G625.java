package cn.blmdz.book;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class G625 extends JFrame implements ActionListener, TreeModelListener {
	
	private static final long serialVersionUID = 1L;
	
	JLabel label = null;
	JTree tree = null;
	DefaultTreeModel treeModel = null;
	String nodeName = null;

	public G625() {
		super("树节点的编辑");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("资源管理器");
		tree = new JTree(root);
		tree.setEditable(true);
		treeModel = (DefaultTreeModel) tree.getModel();
		treeModel.addTreeModelListener(this);
		
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					JTree tree = (JTree) e.getSource();
					TreePath treePath = tree.getSelectionPath();
					TreeNode treeNode = (TreeNode) treePath.getLastPathComponent();
					nodeName = treeNode.toString();
				} catch (NullPointerException ex) {
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
				}
			}
		});
		
		
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setViewportView(tree);
		JPanel panel = new JPanel();
		JButton b = new JButton("新增节点");
		b.addActionListener(this);
		panel.add(b);

		b = new JButton("删除节点");
		b.addActionListener(this);
		panel.add(b);
		
		b = new JButton("清除所有节点");
		b.addActionListener(this);
		panel.add(b);
		
		label = new JLabel("action");
		c.add(panel, BorderLayout.NORTH);
		c.add(jScrollPane, BorderLayout.CENTER);
		c.add(label, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		G625 app = new G625();
		app.pack();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("新增节点")) {
			DefaultMutableTreeNode newnode = new DefaultMutableTreeNode("新节点");
			newnode.setAllowsChildren(true);
			TreePath treepath = tree.getSelectionPath();
			if (treepath == null) {
				label.setText("请选择节点");
				return;
			}
			DefaultMutableTreeNode parentnode = (DefaultMutableTreeNode) treepath.getLastPathComponent();
			treeModel.insertNodeInto(newnode, parentnode, parentnode.getChildCount());
			tree.scrollPathToVisible(new TreePath(newnode.getPath()));
			label.setText("新增节点成功");
		} else if (e.getActionCommand().equals("删除节点")) {
			TreePath treepath = tree.getSelectionPath();
			if (treepath == null) {
				label.setText("请选择节点");
				return;
			}
			DefaultMutableTreeNode parentnode = (DefaultMutableTreeNode) treepath.getLastPathComponent();
			TreeNode parent = parentnode.getParent();
			if (parent == null) {
				label.setText("不能选择主节点");
				return;
			}
			treeModel.removeNodeFromParent(parentnode);
			label.setText("删除节点成功");
			
		} else if (e.getActionCommand().equals("清除所有节点")) {
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
			root.removeAllChildren();
			treeModel.reload();
			label.setText("清除所有节点成功");
		}
	}

}
