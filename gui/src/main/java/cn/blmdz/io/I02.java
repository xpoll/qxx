package cn.blmdz.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;

import lombok.AllArgsConstructor;
import lombok.Data;

public class I02 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
//		File file = new File("a.txt");
//		File file = new File("readme.md");
//		System.out.println(file.exists());
//		if (file.exists()) {
//			System.out.println("exists");
//			System.exit(0);
//		}
//		System.out.println("go");
//		PrintWriter print = new PrintWriter(file);
		PrintWriter print = new PrintWriter("a.txt");
		print.print("q");
		print.println("w");
		print.print("e");
		print.println("r");
		print.close();
		System.out.println(new Scanner(new File("a.txt")).nextLine());
//		file.createNewFile();
//		InputStream
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.dat"));
		oos.writeObject(new Student(1L, "sdf"));
		oos.writeObject(new Student(2L, "sdf"));
		oos.writeObject(new Student(3L, "sdf"));
		oos.writeInt(1);
		oos.close();
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.dat"));
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println(ois.readObject());
		System.out.println(ois.readInt());
		ois.close();
		
		
		
	}
	
}
@Data
@AllArgsConstructor
class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
}