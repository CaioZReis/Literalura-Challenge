package br.com.alura.literalura.repositorio;

import br.com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepositorioAutor extends JpaRepository<Autor, Long> {
    Optional<Autor> findAutorByNome(String nome);

//    @Query("SELECT a FROM autores a")
//    List<Autor> findAllAuthors(String nome);
//
//    @Query("SELECT a FROM Author a WHERE :year BETWEEN a.birthYear AND a.deathYear ORDER BY a.birthYear")
//    List<Autor> findLivingAuthorsByYear(Integer year);
}
