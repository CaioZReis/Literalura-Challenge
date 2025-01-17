package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroResultados(
        @JsonAlias("id") Integer id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorResultado> autores,
        @JsonAlias("languages") List<String> linguas,
        @JsonAlias("download_count") double numeroDowload
        ) {
    @Override
    public String toString() {
        return  "\nTitulo: " + titulo +
                "\nAutor: " + (autores.isEmpty() ? autores: autores.getFirst().nome()) +
                "\nLingua: " + (linguas.isEmpty() ? linguas: linguas.getFirst()) +
                "\nNúmero de Downloads: " + numeroDowload;
    }
}
