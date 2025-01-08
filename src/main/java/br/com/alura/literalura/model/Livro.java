package br.com.alura.literalura.model;

public class Livro {

    private Long apiId;
    private String titulo;
    private Autor autor;
    private String lingua;

    public Livro(Long apiId, String titulo, Autor autor, String lingua){
        this.apiId = apiId;
        this.titulo = titulo;
        this.autor = autor;
        this.lingua = lingua;
    }

    public Long getApiId() {
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

    public void setApiId(Long apiId) {
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
        return "Livro Procurado:" +
                "\nTítulo: " + titulo +
                "\nAutor: " + autor +
                "\nLingua: " + lingua;
    }
}
