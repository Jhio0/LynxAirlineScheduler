package com.example.Lynx;

import org.springframework.boot.SpringApplication;

public class TestLynxApplication {

	public static void main(String[] args) {
		SpringApplication.from(LynxApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
