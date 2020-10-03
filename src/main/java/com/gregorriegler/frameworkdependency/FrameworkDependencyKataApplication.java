package com.gregorriegler.frameworkdependency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FrameworkDependencyKataApplication {

    public static void main(String[] args) {
		SpringApplication.run(FrameworkDependencyKataApplication.class, args);
	}

}
