package br.com.alura.literalura;

import br.com.alura.literalura.principal.Principal;
import br.com.alura.literalura.repositorio.RepositorioAutor;
import br.com.alura.literalura.repositorio.RepositorioLivro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private RepositorioLivro repositorioLivro;
	@Autowired
	private RepositorioAutor repositorioAutor;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioAutor, repositorioLivro);
		principal.exibeMenu();
	}
}
