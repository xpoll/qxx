package site.blmdz.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;

//文件对话框
public class G03 {
	public static void main(String[] args) throws FileNotFoundException {
		JFileChooser jFileChooser = new JFileChooser();
		if(jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = jFileChooser.getSelectedFile();
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				System.out.println(input.nextLine());
			}
			input.close();
		} else {
			System.out.println("未选择");
		}
	}
}
