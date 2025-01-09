package br.com.alura.literalura.repositorio;

import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioLivro extends JpaRepository<Livro, Integer> {
    Optional<Livro> findBookByTitulo(String titulo);

//    @Query("SELECT l FROM livros l")
//    Optional<Livro> findAllBooks(String nome, String autor);
//
//    @Query("SELECT l FROM livros l WHERE l.lingua ILIKE :lingua ORDER BY l.titulo")
//    List<Livro> findBooksByLanguage(String lingua);

}
