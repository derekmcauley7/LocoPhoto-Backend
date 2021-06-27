package com.loco.photo.locophoto


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
class Application {

	static void main(String[] args) {
		SpringApplication.run(Application.class, args)
	}
}