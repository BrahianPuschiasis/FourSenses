package com.example.proyectointegrador;

import com.example.proyectointegrador.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ProyectoIntegradorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoIntegradorApplication.class, args);
	}
}

@Component
class MyCommandLineRunner implements CommandLineRunner {

	@Autowired
	private ReservaRepository reservaRepository;

	@Override
	public void run(String... args) throws Exception {
		// Llamar a la función después de que la aplicación se haya iniciado
		addColumnIfNotExists();
	}

	private void addColumnIfNotExists() {
		reservaRepository.addColumnInsertedAt();
	}
}
