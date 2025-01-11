package br.com.alura.literalura.repositorio;

import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RepositorioLivro extends JpaRepository<Livro, Integer> {
    Optional<Livro> findBookByTitulo(String titulo);

    @Query("SELECT l FROM Livro l JOIN l.autor a WHERE a.nome ILIKE %:nome% ORDER BY a.nome")
    List<Livro> findLivrosByAutor(String nome);

    @Query("SELECT l FROM Livro l ORDER BY l.titulo")
    List<Livro> findLivros();

//    @Query("SELECT DISTINCT l.lingua FROM Livro l ORDER BY l.lingua")
//    List<String> findDiferentesLinguas();

//    @Query("SELECT l FROM livros l WHERE l.lingua ILIKE :lingua ORDER BY l.titulo")
//    List<Livro> findBooksByLanguage(String lingua);

}
