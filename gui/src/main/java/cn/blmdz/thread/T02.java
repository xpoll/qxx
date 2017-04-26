package cn.blmdz.thread;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * JApplet 运行需要将编码设为GBK否则显示乱码(Alt+Enter)
 * @author yongzongyang
 * @date 2017年4月26日
 */
public class T02 extends JApplet implements Runnable {

	private static final long serialVersionUID = 1L;
	private JLabel jlabel = new JLabel("Welcome", JLabel.CENTER);
			
			
	public T02() {
		add(jlabel);
		new Thread(this).start();
	}
	@Override
	public void run() {
		try {
			while(true) {
				if (jlabel.getText() == null) {
					jlabel.setText("Welcome");
				} else {
					jlabel.setText(null);
				}
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new T02();
			}
		});
	}

}
