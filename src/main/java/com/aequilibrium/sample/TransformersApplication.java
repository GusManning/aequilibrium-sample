package com.aequilibrium.sample;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableJpaRepositories("com.aequilibrium.sample.transformer")
@EnableWebMvc
public class TransformersApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransformersApplication.class, args);
	}


}
