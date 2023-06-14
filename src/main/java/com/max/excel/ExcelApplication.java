package com.max.excel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExcelApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ExcelApplication.class, args);
		Runtime runtime = Runtime.getRuntime();
		long memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println((double)memory/1024/1024);
	}

}
