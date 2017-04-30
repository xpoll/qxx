package cn.blmdz.book.g11;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

public class ChatPanelSocket extends ChatPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	protected Socket socket;
	
	protected DataInputStream input;
	
	protected DataOutputStream output;
	
	protected Thread thread;
	
	public ChatPanelSocket() {
	}

	@Override
	protected void send() {
		try {
			output.writeUTF(name + "说: " + textField.getText());
			textArea.append("我说: " + textField.getText() + "\n");
			textField.setText(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void exit() {
		try {
			output.writeUTF("bye");
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void close() {
		send.setEnabled(false);
		exit.setEnabled(false);
		dotask.setEnabled(true);
		textArea.append("连接断开!");
		
		try {
			socket.close();
			input.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		System.out.println("线程启动");
		String in = "";
		while(true) {
			try {
				in = input.readUTF();
				if ("bye".equals(in)) {
					this.close();
					break;
				} else {
					textArea.append(in + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
				this.close();
				break;
			}
		}
		System.out.println("线程结束");
	}

	public static void main(String[] args) {
		JFrame app = new JFrame("具有通讯功能的聊天界面");
		app.getContentPane().add(new ChatPanelSocket());
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setSize(500, 300);
		app.setVisible(true);
	}
}
