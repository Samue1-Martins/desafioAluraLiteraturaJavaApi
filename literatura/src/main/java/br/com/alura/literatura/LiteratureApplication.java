package br.com.alura.literatura;

import br.com.alura.literatura.main.Main;
import br.com.alura.literatura.repository.LiteratureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteratureApplication implements CommandLineRunner {

	@Autowired
	private LiteratureRepository literatureRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteratureApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(literatureRepository);
		main.principal();
	}
}
