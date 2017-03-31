package site.blmdz.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JPanelExample extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JButton[] buttons;
	JPanel jpanel;
	CustomPanel panel;
	
	public JPanelExample() {
		super("面板示例");
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		jpanel = new JPanel(new FlowLayout());
		buttons = new JButton[4];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton("按钮" + (i + 1));
			jpanel.add(buttons[i]);
		}
		panel = new CustomPanel();
		container.add(jpanel, BorderLayout.NORTH);
		container.add(panel, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	class CustomPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("welcome eee", 20, 20);
//			g.drawRect(20, 40, 130, 130);
			g.setColor(Color.green);
			g.fillRect(20, 40, 130, 130);
//			g.drawOval(160, 40, 100, 100);
			g.setColor(Color.orange);
			g.fillOval(160, 40, 100, 100);
		}
		public Dimension getPreferredSize() {
			return new Dimension(200, 200);
		}
		
	}
	public static void main(String[] args) {
		JPanelExample j = new JPanelExample();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
