package com.blmdz.primary.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.blmdz.primary.test.PrimaryInterface;

@SpringBootApplication
@ComponentScan("com.blmdz.primary")
public class PrimaryTest implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(PrimaryTest.class);
		application.run(args);
	}
	@Autowired
	private PrimaryInterface face;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(face.show("show"));
	}
}
