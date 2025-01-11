package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorResultado(
        @JsonAlias("name") String nome,
        @JsonAlias("birth_year") Integer anoNascimento,
        @JsonAlias("death_year") Integer anoFalescimento
) {

}
