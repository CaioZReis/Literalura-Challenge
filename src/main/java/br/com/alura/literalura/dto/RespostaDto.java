package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespostaDto(
        @JsonAlias("count") Integer quantidade,
        @JsonAlias("results") List<LivroResultados> livroResultadosApi
) {

}
