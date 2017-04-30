package cn.blmdz.book.g11;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class BaseJPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	protected int size;
	
	protected JLabel[] labels;
	
	protected JTextField[] fields;
	
	protected JButton dotask;
	
	protected JLabel promptLabel;
	
	protected JTextArea textArea;
	
	JPanel south;
	
	public BaseJPanel(int size) {
		this.size = size;
		setLayout(new BorderLayout());
		labels = new JLabel[size];
		fields = new JTextField[size];
		for (int i = 0; i < size; i++) {
			labels[i] = new JLabel("标签" + i, SwingConstants.RIGHT);
		}
		for (int i = 0; i < size; i++) {
			fields[i] = new JTextField(12);
		}
		JPanel innerPanelCenter = new JPanel();
		for (int i = 0; i < size; i++) {
			JPanel innerPanel = new JPanel();
			innerPanel.add(labels[i]);
			innerPanel.add(fields[i]);
			innerPanelCenter.add(innerPanel);
		}
		dotask = new JButton("确定");
		innerPanelCenter.add(dotask);
		promptLabel = new JLabel("设置提示!");
		promptLabel.setForeground(Color.red);
		promptLabel.setBorder(BorderFactory.createTitledBorder("提示"));
		JPanel north = new JPanel(new BorderLayout());
		north.add(innerPanelCenter, BorderLayout.CENTER);
		north.add(promptLabel, BorderLayout.SOUTH);
		add(north, BorderLayout.NORTH);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("幼圆", Font.PLAIN, 16));
		textArea.setEditable(false);
		add(new JScrollPane(textArea), BorderLayout.CENTER);
		
		this.south();
		add(south, BorderLayout.SOUTH);
	}
	
	protected void south() {
		south = new JPanel();
		south.add(new JLabel("子类中需要重写南边的图形组件，以满足不同要求!"));
	}
	
	public static void main(String[] args) {
		JFrame app = new JFrame("通用图形界面");
		BaseJPanel g = new BaseJPanel(2);
		g.labels[0].setText("姓名");
		app.getContentPane().add(g, BorderLayout.CENTER);
		app.setSize(600, 300);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

}
