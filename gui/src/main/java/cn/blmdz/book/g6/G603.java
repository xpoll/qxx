package cn.blmdz.book.g6;

import javax.swing.JOptionPane;

public class G603 {

	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "同桌的你");
		JOptionPane.showConfirmDialog(null, "你在他乡还好吗？");
		JOptionPane.showInputDialog(null, "你在他乡还好吗？");
		String[] s = {"好", "不好"};
		JOptionPane.showInputDialog(null, "你在他乡还好吗？", "输入", JOptionPane.QUESTION_MESSAGE, null, s, s[0]);
	}
}
