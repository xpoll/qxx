package cn.blmdz.book.g8;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.blmdz.book.g8.JdbcUtil.CallBackNoResult;

/**
 * @author lm
 * 登陆
 */
public class LoginJFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton loginBtn, clearBtn, registerBtn, resetBtn, outBtn;
	private JLabel usernameLabel, passwordLabel;
	private JTextField usernameText;
	private JPasswordField passwordText;
	private JDialog msg;
	private int massage = 0;
	
	public LoginJFrame() {
		super("电信IP资费管理系统");
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		
		usernameLabel = new JLabel("用户姓名");
		passwordLabel = new JLabel("用户口令");
		usernameText = new JTextField(10);
		passwordText = new JPasswordField(10);
		
		loginBtn = new JButton("登录");
		clearBtn = new JButton("清除");
		registerBtn = new JButton("注册");
		resetBtn = new JButton("重新输入");
		outBtn = new JButton("退出");
		
		loginBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		registerBtn.addActionListener(this);
		resetBtn.addActionListener(this);
		outBtn.addActionListener(this);
		
		c.add(usernameLabel);
		c.add(usernameText);
		c.add(passwordLabel);
		c.add(passwordText);
		
		c.add(loginBtn);
		c.add(clearBtn);
		c.add(registerBtn);
		c.add(new JLabel("如果你还没有注册，请注册"));
		
		msg = new JDialog();
		msg.setTitle("提示");
		msg.setSize(340, 80);
		msg.setLocation(this.getX() + 100, this.getY() + 100);
		msg.setLayout(new FlowLayout());
		msg.add(new JLabel("重新输入还是退出？"));
		msg.add(resetBtn);
		msg.add(outBtn);

		this.setSize(200, 200);
		this.setLocation(360, 240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginBtn) {
			final String sql = "select * from t_consumer";
			JdbcUtil.connection(new CallBackNoResult() {
				@Override
				public void execute(Statement stmt, ResultSet rs) throws SQLException {
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						String id = rs.getString("id");
						String password = rs.getString("password");
						char[] ps = passwordText.getPassword();
						String str = "";
						for (char c : ps) {
							str += c;
						}
						if (id.equals(usernameText.getText()) && password.equals(str)) {
							massage = 1;
							// TODO 新建菜单
							break;
						}
					}
				}
			});
			if (massage == 0) {
				JOptionPane.showMessageDialog(this, "账号或密码有误", "系统提示", JOptionPane.ERROR_MESSAGE);
			} else if (massage == 1) {
				setVisible(false);
			}
			
		} else if (e.getSource() == clearBtn) {
			msg.setVisible(true);
		} else if (e.getSource() == registerBtn) {
			new RegisterJFrame();
			this.setVisible(false);
		} else if (e.getSource() == resetBtn) {
			usernameText.setText(null);
			passwordText.setText(null);
			msg.setVisible(false);
		} else if (e.getSource() == outBtn) {
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		new LoginJFrame();
	}
}
