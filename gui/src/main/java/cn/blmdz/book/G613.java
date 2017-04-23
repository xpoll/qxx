package cn.blmdz.book;

import java.awt.Component;
import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class G613 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public G613() {
		super("组布局管理器的使用-查找");
		Container c = getContentPane();
		JLabel label1 = new JLabel("查找：");
		JTextField jTextField = new JTextField();
		JCheckBox cb1 = new JCheckBox("不区分大小写");
		JCheckBox cb2 = new JCheckBox("区分大小写");
		JRadioButton rb1 = new JRadioButton("向上");
		JRadioButton rb2 = new JRadioButton("向下");
		JButton findBtn = new JButton("查找下一个");
		JButton cancelBtn = new JButton("取消");
		GroupLayout groupLayout = new GroupLayout(c);
		c.setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		
		GroupLayout.ParallelGroup pg1 = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		pg1.addComponent(cb1);
		pg1.addComponent(cb2);

		GroupLayout.ParallelGroup pg2 = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		pg2.addComponent(rb1);
		pg2.addComponent(rb2);
		
		GroupLayout.SequentialGroup sg = groupLayout.createSequentialGroup();
		sg.addGroup(pg1);
		sg.addGroup(pg2);

		GroupLayout.ParallelGroup pg3 = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		pg3.addComponent(jTextField);
		pg3.addGroup(sg);

		GroupLayout.ParallelGroup pg4 = groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		pg4.addComponent(findBtn);
		pg4.addComponent(cancelBtn);
		
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup().addComponent(label1).addContainerGap().addGroup(pg3).addGroup(pg4));
		groupLayout.linkSize(SwingConstants.HORIZONTAL,new Component[] {findBtn, cancelBtn});
		
		GroupLayout.ParallelGroup pg5 = groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		pg5.addComponent(label1);
		pg5.addComponent(jTextField);
		pg5.addComponent(findBtn);
		
		GroupLayout.ParallelGroup pg6 = groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		pg6.addComponent(cb1);
		pg6.addComponent(rb1);
		pg6.addComponent(cancelBtn);
		
		GroupLayout.ParallelGroup pg7 = groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
		pg7.addComponent(cb2);
		pg7.addComponent(rb2);
		groupLayout.setVerticalGroup(groupLayout.createSequentialGroup().addGroup(pg5).addGroup(pg6).addGroup(pg7));
		
	}
	
	public static void main(String[] args) {
		G613 app = new G613();
		app.setSize(400, 200);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}