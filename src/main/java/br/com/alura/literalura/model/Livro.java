package br.com.alura.literalura.model;

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
    private double numeroDowload;

    public Livro(){

    }

    public Livro(LivroResultados livroResultados){

        Autor autorNaoEncontrado = new Autor();
        setApiId(livroResultados.id());
        setTitulo(livroResultados.titulo());
        setAutor(autorNaoEncontrado);
        setLingua(livroResultados.linguas().getFirst());
        setNumeroDowload(livroResultados.numeroDowload());
    }
    public Livro(LivroResultados livroResultados, Autor autorResultado){

        setApiId(livroResultados.id());
        setTitulo(livroResultados.titulo());
        setAutor(autorResultado);
        setLingua(livroResultados.linguas().getFirst());
        setNumeroDowload(livroResultados.numeroDowload());
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
        if (autor.getNome().equals("Autor não encontrado")){
            this.autor.setAnoNascimento(0);
            this.autor.setAnoFalecimento(0);
        } else {
            this.autor.setAnoNascimento(autor.getAnoNascimento());
            this.autor.setAnoFalecimento(autor.getAnoFalecimento());
        }


        this.autor = autor;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    public double getNumeroDowload() {
        return numeroDowload;
    }

    public void setNumeroDowload(double numeroDowload) {
        this.numeroDowload = numeroDowload;
    }

    @Override
    public String toString() {
        return "Título: " + titulo +
                " | Autor: " + autor.getNome() +
                " | Lingua: " + lingua +
                " | Total de Downloads: " + numeroDowload;
    }
}
