package site.blmdz.gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

//显示gui组件
public class G02 {
	public static void main(String[] args) {
		JButton jButtonOK = new JButton("OK");
		JButton jButtonEXIT = new JButton("EXIT");
		JLabel jLabel = new JLabel("你的名字：");
		JTextField jTextField = new JTextField("this text name.");
		JCheckBox jCheckBox1 = new JCheckBox("红");
		JCheckBox jCheckBox2 = new JCheckBox("绿");
		JRadioButton jRadioButton1 = new JRadioButton("黑");
		JRadioButton jRadioButton2 = new JRadioButton("白");
		JComboBox<String> jComboBox = new JComboBox<String>(new String[]{"A", "B", "C"});
		JPanel jPanel = new JPanel();
		jPanel.add(jButtonOK);
		jPanel.add(jButtonEXIT);
		jPanel.add(jLabel);
		jPanel.add(jTextField);
		jPanel.add(jCheckBox1);
		jPanel.add(jCheckBox2);
		jPanel.add(jRadioButton1);
		jPanel.add(jRadioButton2);
		jPanel.add(jComboBox);

		JFrame jFrame = new JFrame();
		jFrame.add(jPanel);
		jFrame.setTitle("this`s title");
		jFrame.setSize(600, 300);//大小
		jFrame.setLocation(500, 200);//位置
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
		
		
	}
}
