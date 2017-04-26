package cn.blmdz.book.g6;

import java.awt.BorderLayout;
import java.awt.Container;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class G703 extends JFrame {

	private static final long serialVersionUID = 1L;

	Object data[][];
	Object colname[] = {"学号", "姓名", "年龄", "专业"};
	JTable studentTable;
	
	public G703() {
		super("通过Mysql访问数据库");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		data = new Object[50][10];
		this.loadData();
		
		studentTable = new JTable(data, colname);
		c.add(new JScrollPane(studentTable), BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		G703 app = new G703();
		app.setSize(550, 250);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
	
	private void loadData() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test", "root", "root");
//			Statement stmt = conn.createStatement();
			String sql = "select * from studentinfo";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			int i = 0;
			while(rs.next()) {
				data[i][0] = rs.getString(1);
				data[i][1] = rs.getString(2);
				data[i][2] = rs.getString(3);
				data[i][3] = rs.getString(4);
				i ++;
			}
			rs.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
