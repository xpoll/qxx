package cn.blmdz.book.g11;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatPanel extends BaseJPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	protected JTextField textField;
	
	protected JButton send;
	
	protected JButton exit;
	
	protected String name;

	public ChatPanel() {
		super(1);
		labels[0].setText("输入昵称");
		promptLabel.setText("点击[确定],连接服务器并开始聊天");
		dotask.addActionListener(this);
		send.addActionListener(this);
		exit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == dotask) {
			dotask();
		}else if (e.getSource() == send) {
			send();
		} else if (e.getSource() == exit) {
			exit();
		}
	}

	protected void dotask() {}
	
	protected void send() {}
	
	protected void exit() {}
	
	@Override
	protected void south() {
		south = new JPanel();
		textField = new JTextField(20);
		south.add(textField);
		send = new JButton("发送");
		exit = new JButton("离线");
		send.setEnabled(false);
		exit.setEnabled(false);
		south.add(send);
		south.add(exit);
	}
	
	public static void main(String[] args) {
		JFrame app = new JFrame("聊天界面");
		app.getContentPane().add(new ChatPanel());
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setSize(500, 300);
		app.setVisible(true);
	}
}
