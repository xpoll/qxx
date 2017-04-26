package cn.blmdz.book.g6;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class G619 extends JFrame {

	private static final long serialVersionUID = 1L;

	DefaultTableModel dt = new DefaultTableModel();;
	JTable table;
	
	public G619() {
		super("表格的使用");
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		JButton[] b = {
				new JButton("添加行"), new JButton("添加列"),
				new JButton("删除行"), new JButton("删除列")
		};
		for (int i = 0; i < b.length; i++) {
			b[i].addActionListener(new TableActionListener(i));
			c.add(b[i]);
		}
		table = new JTable(dt);
		
		table.setPreferredScrollableViewportSize(new Dimension(360,  160));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane sPane = new JScrollPane(table);
		c.add(sPane);
		
	}
	
	class TableActionListener implements ActionListener {

		private int index;
		
		public TableActionListener(int index) {
			this.index = index;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			add(index);
		}
	}
	
	private void add(Integer index) {
		switch (index) {
		case 0:
			int col = dt.getColumnCount();
			if (col == 0) {
				add(1);
			}
			int r = rowCurrent();
			System.out.println("当前行号为：" + r);
			dt.insertRow(r, (Vector<?>)null);
			System.out.println("添加一行");
			break;
		case 1:
			int col1 = dt.getColumnCount();
			int row1 = dt.getRowCount();
			String s1 = "列" + (col1 + 1);
			int c1 = table.getSelectedColumn();
			System.out.println("当前列号为：" + c1);
			if (col1 == 0 || row1 == 0 || c1 < 0) {
				dt.addColumn(s1);
				return;
			}
			Vector<String> vs = colNames();
			vs.add(c1, s1);
			@SuppressWarnings("unchecked")
			Vector<Vector<String>> date = dt.getDataVector();
			for (int i = 0; i < date.size(); i++) {
				Vector<String> de = date.get(i);
				de.add(c1, new String(""));
			}
			dt.setDataVector(date, vs);
			System.out.println("添加一列");
			break;
		case 2:
			if (dt.getRowCount() > 0) {
				dt.removeRow(rowCurrent());
			}
			System.out.println("删除一行");
			break;
		case 3:
			int col3 = dt.getColumnCount();
			if (col3 == 0) {
				return;
			}
			int c3 = table.getSelectedColumn();
			Vector<String> vs3 = colNames();
			vs3.remove(c3);
			@SuppressWarnings("unchecked")
			Vector<Vector<String>> date3 = dt.getDataVector();
			for (int i = 0; i < date3.size(); i++) {
				Vector<String> de = date3.get(i);
				de.remove(c3);
			}
			dt.setDataVector(date3, vs3);
			System.out.println("删除一列");
			break;
		}
	}
	
	private Vector<String> colNames() {
		Vector<String> vs = new Vector<String>();
		int col = dt.getColumnCount();
		for (int i = 0; i < col; i++) {
			vs.add(dt.getColumnName(i));
		}
		return vs;
	}
	
	private int rowCurrent() {
		int row = table.getSelectedRow();
		return (row < 0) ? 0 : row;
	}

	public static void main(String[] args) {
		G619 app = new G619();
		app.setSize(360, 260);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
