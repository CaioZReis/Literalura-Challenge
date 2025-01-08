package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroProcurado(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String titulo
) {
}
