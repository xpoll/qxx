package cn.blmdz.gui;

import javax.swing.JOptionPane;
//gui弹窗
public class G01 {
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "你好，世界！", "标题。", JOptionPane.ERROR_MESSAGE);
		System.out.println(JOptionPane.showInputDialog("输入。", "初始化。"));
		System.out.println(JOptionPane.showConfirmDialog(null, "你确定 ?"));
		
	}
}
