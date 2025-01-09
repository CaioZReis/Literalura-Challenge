package br.com.alura.literalura.model;


import br.com.alura.literalura.dto.AutorResultado;
import br.com.alura.literalura.dto.LivroResultados;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    private Integer apiId;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String lingua;

    public Livro(){

    }

    public Livro(LivroResultados livroResultados){

        Autor autorNaoEncontrado = new Autor();
        setApiId(livroResultados.id());
        setTitulo(livroResultados.titulo());
        setAutor(autorNaoEncontrado);
        setLingua(livroResultados.linguas().getFirst());
    }
    public Livro(LivroResultados livroResultados, Autor autorResultado){

        setApiId(livroResultados.id());
        setTitulo(livroResultados.titulo());
        setAutor(autorResultado);
        setLingua(livroResultados.linguas().getFirst());
    }

    public Integer getApiId() {
        return apiId;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getLingua() {
        return lingua;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    @Override
    public String toString() {
        return "Título: " + titulo +
                " | Autor: " + autor.getNome() +
                " | Lingua: " + lingua;
    }
}
