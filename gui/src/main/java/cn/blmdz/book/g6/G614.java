package cn.blmdz.book.g6;

import java.awt.Button;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class G614 extends JFrame {
	
	private static final long serialVersionUID = 1L;
	

	public G614() {
		super("事件处理的简单应用");
		
//		class G614Handle implements ActionListener {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("事件处理的简单应用");
//			}
//		}
		
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		Button btn = new Button("button");
//		btn.addActionListener(new G614Handle());
		btn.addActionListener(new ActionListener() {
			int i = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				Button b = (Button) e.getSource();
				b.setLabel(String.valueOf(i++));
			}
		});
		c.add(btn);
		JButton jbtn = new JButton("button");
		jbtn.addActionListener(new ActionListener() {
			int i = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				b.setText(String.valueOf(i++));
			}
		});
		c.add(jbtn);
	}
	public static void main(String[] args) {
		G614 app = new G614();
		app.setSize(160, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
	
}
