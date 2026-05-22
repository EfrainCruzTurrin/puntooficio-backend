package com.puntooficio.puntooficio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PuntooficioApplication {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("12345678"));
		SpringApplication.run(PuntooficioApplication.class, args);
	}
}
