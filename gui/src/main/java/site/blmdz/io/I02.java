package site.blmdz.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class I02 {

	public static void main(String[] args) throws IOException {
		
		File file = new File("a.txt");
//		File file = new File("readme.md");
		System.out.println(file.exists());
		if (file.exists()) {
			System.out.println("exists");
			System.exit(0);
		}
		System.out.println("go");
		PrintWriter print = new PrintWriter(file);
		print.print("q");
		print.println("w");
		print.print("e");
		print.println("r");
		print.close();
//		file.createNewFile();
	}
}
