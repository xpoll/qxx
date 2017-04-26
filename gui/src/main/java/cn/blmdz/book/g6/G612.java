package cn.blmdz.book.g6;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;

public class G612 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public G612() {
		super("卡片布局管理器的使用");
		Container c = getContentPane();
		CardLayout card = new CardLayout();
		c.setLayout(card);
		
		for (int i = 0; i < 6; i++) {
			String s = "按钮" + (i + 1);
			JButton b = new JButton(s);
			c.add(b, s);
			System.out.println(s);
		}
		
		card.show(c,  "按钮2");
		card.next(c);
	}
	
	public static void main(String[] args) {
		G612 app = new G612();
		app.setSize(360, 160);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
