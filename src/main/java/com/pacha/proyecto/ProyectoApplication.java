package com.pacha.proyecto;

import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ProyectoApplication implements CommandLineRunner {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

	public void run(String... args) throws Exception {
		System.out.println("_____corre_________");
		// System.out.println("password: " + new
		// BCryptPasswordEncoder().encode("123456"));

	}

}
