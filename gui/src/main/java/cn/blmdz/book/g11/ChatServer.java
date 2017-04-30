package cn.blmdz.book.g11;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JFrame;

public class ChatServer extends ChatPanelSocket {

	private static final long serialVersionUID = 1L;

	public ChatServer() {
		promptLabel.setText("服务器聊天程序, 应先启动后等待客户连接...");
	}
	
	@Override
	@SuppressWarnings("resource")
	public void dotask() {
		try {
			textArea.append("启动服务器。\n");
			name = fields[0].getText();
			textArea.append("等待客户端连接...\n");
			socket = new ServerSocket(5555).accept();
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			thread = new Thread(this);
			thread.start();
			dotask.setEnabled(false);
			send.setEnabled(true);
			exit.setEnabled(true);
			output.writeUTF("hi!\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JFrame app = new JFrame("服务器端聊天界面");
		app.getContentPane().add(new ChatServer());
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setSize(500, 300);
		app.setVisible(true);
	}

}
