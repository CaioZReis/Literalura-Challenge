package br.com.alura.literalura.repositorio;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepositorioAutor extends JpaRepository<Autor, String> {
    Optional<Autor> findAutorByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE :ano BETWEEN a.anoNascimento AND a.anoFalecimento ORDER BY a.anoNascimento")
    List<Autor> encontrarAutorPorAno(Integer ano);
}
