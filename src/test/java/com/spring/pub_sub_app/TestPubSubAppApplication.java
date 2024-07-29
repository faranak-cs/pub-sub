package com.spring.pub_sub_app;

import org.springframework.boot.SpringApplication;

public class TestPubSubAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(PubSubAppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
