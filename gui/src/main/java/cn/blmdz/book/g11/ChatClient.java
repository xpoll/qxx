package cn.blmdz.book.g11;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class ChatClient extends ChatPanelSocket {

	private static final long serialVersionUID = 1L;

	public ChatClient() {
		promptLabel.setText("客户端聊天程序, 服务端已启动后, 才能连接, 开始聊天...");
	}
	
	@Override
	public void dotask() {
		try {
			textArea.append("连接服务器。\n");
			name = fields[0].getText();
			textArea.append(name + "连接服务器。\n");
			socket = new Socket("127.0.0.1", 5555);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			thread = new Thread(this);
			thread.start();
			dotask.setEnabled(false);
			send.setEnabled(true);
			exit.setEnabled(true);
			output.writeUTF("hi!\n");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		JFrame app = new JFrame("客户端聊天界面");
		app.getContentPane().add(new ChatClient());
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setSize(500, 300);
		app.setVisible(true);
	}
}
