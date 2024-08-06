package dev.app.cocospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling   // selected coin price 주기적으로 받아오기 위해 사용
@SpringBootApplication
public class CocoSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CocoSpringApplication.class, args);
	}

}
