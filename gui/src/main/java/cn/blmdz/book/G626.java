package cn.blmdz.book;

import java.awt.Container;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class G626 extends JFrame implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;

	JEditorPane editorPane = null;
	
	public G626() {
		super("TreeSelectionEvent的使用");
		Container c = getContentPane();

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("资源管理器");
		root.add(new DefaultMutableTreeNode("pom.xml"));
		root.add(new DefaultMutableTreeNode("readme.md"));
		
		JTree tree = new JTree(root);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		
		JScrollPane scrollPane = new JScrollPane(tree);
		editorPane = new JEditorPane();

		JScrollPane scrollPane2 = new JScrollPane(editorPane);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, scrollPane2);
		
		c.add(splitPane);
	}
	
	public static void main(String[] args) {
		G626 app = new G626();
		app.pack();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		JTree tree = (JTree) e.getSource();
		DefaultMutableTreeNode selection = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		String name = selection.toString();
		if (selection.isLeaf()) {
			String filePath = "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + name;
			System.out.println(filePath);
			try {
				editorPane.setPage(filePath);
			} catch (IOException e1) {
				System.out.println("找不到文件");
			}
		}
	}
}
