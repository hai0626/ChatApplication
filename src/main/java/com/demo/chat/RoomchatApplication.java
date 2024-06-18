package com.demo.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class RoomchatApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomchatApplication.class, args);
	}

}
