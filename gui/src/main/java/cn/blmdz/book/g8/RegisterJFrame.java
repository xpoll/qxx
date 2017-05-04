package cn.blmdz.book.g8;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cn.blmdz.book.g8.JdbcUtil.CallBackNoResult;

public class RegisterJFrame extends JFrame implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	
	private JTextField usernameTextField, passwordTextField, surePasswordTextField, nameTextField, phoneTextField, emailTextField;
	private JRadioButton boyBtn, grilBtn;
	private JRadioButton transferBtn, cashBtn, remitBtn, otherBtn;
	private JComboBox<String> provinceBox, openStatusBox;
	private JButton nextBtn, resetBtn, retuBtn;

	public RegisterJFrame() {
		super("现在注册");
		this.setSize(600, 500);
		this.setLocation(200, 140);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		Container c = getContentPane();
		c.setLayout(new GridLayout(1, 4));
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p31 = new JPanel();
		JPanel p32 = new JPanel();
		JPanel p4 = new JPanel();
		p1.setLayout(new GridLayout(12, 1, 0, 10));
		p2.setLayout(new GridLayout(12, 1, 0, 10));
		p3.setLayout(new GridLayout(12, 1, 0, 10));
		p31.setLayout(new GridLayout(1, 2));
		p32.setLayout(new GridLayout(2, 2));
		
		c.add(p1);
		c.add(p2);
		c.add(p3);
		c.add(p4);
		
		p1.add(new JLabel("    注册步骤"));
		p1.add(new JLabel("    一：阅读并同意协议"));
		p1.add(new JLabel("    二：填写表单"));
		p1.add(new JLabel("    三：完成注册"));
		usernameTextField = new JTextField(10);
		emailTextField = new JTextField(10);
		passwordTextField = new JPasswordField(10);
		surePasswordTextField = new JPasswordField(10);
		nameTextField = new JTextField(10);
		phoneTextField = new JTextField(10);
		
		ButtonGroup sexGroup = new ButtonGroup();
		boyBtn = new JRadioButton("男");
		grilBtn = new JRadioButton("女");
		sexGroup.add(boyBtn);
		sexGroup.add(grilBtn);
		
		ButtonGroup wayGroup = new ButtonGroup();
		transferBtn = new JRadioButton("银行转账");
		cashBtn = new JRadioButton("现金支付");
		remitBtn = new JRadioButton("邮政汇款");
		otherBtn = new JRadioButton("其他");
		wayGroup.add(transferBtn);
		wayGroup.add(cashBtn);
		wayGroup.add(remitBtn);
		wayGroup.add(otherBtn);
		
		String[] provinces = {"河南", "北京", "上海"};
		provinceBox = new JComboBox<String>(provinces);

		String[] opens = {"开通", "未开通"};
		openStatusBox = new JComboBox<String>(opens);

		p31.add(boyBtn);
		p31.add(grilBtn);
		p32.add(transferBtn);
		p32.add(cashBtn);
		p32.add(remitBtn);
		p32.add(otherBtn);

		p2.add(new JLabel("用户名"));
		p2.add(new JLabel("性别"));
		p2.add(new JLabel("密码"));
		p2.add(new JLabel("确认密码"));
		p2.add(new JLabel("姓名"));
		p2.add(new JLabel("电话"));
		p2.add(new JLabel("省份"));
		p2.add(new JLabel("付款方式"));
		p2.add(new JLabel("开通状态"));
		p2.add(new JLabel("Email"));
		
		p3.add(usernameTextField);
		p3.add(p31);
		p3.add(passwordTextField);
		p3.add(surePasswordTextField);
		p3.add(nameTextField);
		p3.add(phoneTextField);
		p3.add(provinceBox);
		p3.add(p32);
		p3.add(openStatusBox);
		p3.add(emailTextField);
		
		nextBtn = new JButton("下一步");
		resetBtn = new JButton("重新填写");
		retuBtn =  new JButton("返回");
		
		nextBtn.addActionListener(this);
		resetBtn.addActionListener(this);
		retuBtn.addActionListener(this);

		p1.add(retuBtn);
		p2.add(nextBtn);
		p3.add(resetBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == nextBtn) {
			if (passwordTextField.getText().equals(surePasswordTextField.getText())) {
				JdbcUtil.connection(new CallBackNoResult() {
					@Override
					public void execute(Statement stmt, ResultSet rs) throws SQLException {
						String xb = ""; // 性别
						if (boyBtn.isSelected()) {
							xb = boyBtn.getText();
						}
						if (grilBtn.isSelected()) {
							xb = grilBtn.getText();
						}
						String sf = ""; // 省份
						if (provinceBox.getSelectedIndex() == 0) {
							sf = "河南";
						}
						if (provinceBox.getSelectedIndex() == 1) {
							sf = "北京";
						}
						if (provinceBox.getSelectedIndex() == 2) {
							sf = "上海";
						}
						String fs = ""; // 付款方式
						if (transferBtn.isSelected()) {
							fs = transferBtn.getText();
						}
						if (cashBtn.isSelected()) {
							fs = cashBtn.getText();
						}
						if (remitBtn.isSelected()) {
							fs = remitBtn.getText();
						}
						if (otherBtn.isSelected()) {
							fs = otherBtn.getText();
						}
						String zt = ""; // 开通状态
						if (openStatusBox.getSelectedIndex() == 0) {
							zt = "开通";
						}
						if (openStatusBox.getSelectedIndex() == 1) {
							zt = "未开通";
						}
						String sql = "insert into t_consumer (`id`,`password`,`name`,`sex`,`method`,`province`,`telephone`,`mailaddress`,`date`,`state`)"
								+ " values('" + usernameTextField.getText() + "',"
										+ "'" + passwordTextField.getText() + "',"
										+ "'" + nameTextField.getText() + "',"
										+ "'" + xb + "',"
										+ "'" + fs + "',"
										+ "'" + sf + "',"
										+ "'" + phoneTextField.getText() + "',"
										+ "'" + emailTextField.getText() + "',"
										+ "now(),"
										+ "'" + zt + "')";
						System.out.println(sql);
						stmt.executeUpdate(sql);
					}
				});
			}
			JOptionPane.showMessageDialog(this, "两次密码不一致", "系统提示", JOptionPane.ERROR_MESSAGE);
		} else if (e.getSource() == resetBtn) {
			usernameTextField.setText(null);
			passwordTextField.setText(null);
			surePasswordTextField.setText(null);
			nameTextField.setText(null);
			phoneTextField.setText(null);
			emailTextField.setText(null);
		} else if (e.getSource() == retuBtn) {
			new LoginJFrame();
			this.setVisible(false);
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	}

}
