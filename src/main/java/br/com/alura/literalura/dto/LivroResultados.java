package br.com.alura.literalura.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroResultados(
        @JsonAlias("id") Integer id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorResultado> autores,
        @JsonAlias("languages") List<String> linguas
) {
    @Override
    public String toString() {
        return  "\nId: " + id +
                "\nTitulo: " + titulo +
                "\nAutor: " + autores.getFirst() +
                "\nLingua: " + linguas.getFirst();
    }
}
