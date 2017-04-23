package cn.blmdz.book.g703;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class StudentManager extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JTabbedPane tabbedPane;
	StudentPanel inputInnerPanel = new StudentPanel();
	JButton inputBtn = new JButton("录入");
	Connection conn;
	Statement stmt;
	
	public StudentManager() {
		super("学生基本信息管理系统");
		setGUIComponent();
	}
	
	public void setGUIComponent() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane(); // 选项卡
		inputBtn.addActionListener(this);
		
		tabbedPane.add("录入记录", inputInnerPanel);
		c.add(BorderLayout.CENTER, tabbedPane);
		c.add(BorderLayout.SOUTH, inputBtn);
	}
	
	public void connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test", "root", "root");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void inputRecords() {
		try {
			connection();
			String sql = "insert into student (no, name, gender, birth, address, tel) values"
					+ "("
					+ "'" + inputInnerPanel.getNoField() + "',"
					+ "'" + inputInnerPanel.getNameField() + "',"
					+ "'" + inputInnerPanel.getGenderField() + "',"
					+ "'" + inputInnerPanel.getBirthField() + "',"
					+ "'" + inputInnerPanel.getAddressField() + "',"
					+ "'" + inputInnerPanel.getTelField() + "'"
					+ ")";
			stmt.execute(sql);
			JOptionPane.showMessageDialog(null, "添加成功！", "标题。", JOptionPane.INFORMATION_MESSAGE);
			inputInnerPanel.clearContent();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getSQLState(), "标题。", JOptionPane.ERROR_MESSAGE);
		} finally {
			close();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == inputBtn) {
			inputRecords();
		}
	}
	
	public static void main(String[] args) {
		StudentManager app = new StudentManager();
		app.setSize(550, 180);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
