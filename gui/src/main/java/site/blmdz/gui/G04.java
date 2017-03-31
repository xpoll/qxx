package site.blmdz.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class G04 {

	public static void main(String[] args) {
		JFrame jf = new JFrame("radio");
		Container container = jf.getContentPane();
		container.setLayout(new FlowLayout());
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new GridLayout(1, 3));
		jpanel.setBorder(BorderFactory.createTitledBorder("选择"));
		
		JRadioButton r1 = new JRadioButton("beijing");
		JRadioButton r2 = new JRadioButton("shanghai");
		JRadioButton r3 = new JRadioButton("henan");
		jpanel.add(r1);
		jpanel.add(r2);
		jpanel.add(r3);
		
		r1.setSelected(true);
		container.add(jpanel);
		jf.pack();
		jf.setVisible(true);
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
	}
}
