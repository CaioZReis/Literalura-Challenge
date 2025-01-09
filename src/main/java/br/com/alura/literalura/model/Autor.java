package br.com.alura.literalura.model;

import br.com.alura.literalura.dto.AutorResultado;
import jakarta.persistence.*;

@Entity
@Table(name = "autores", uniqueConstraints = {@UniqueConstraint(columnNames = "nome")})
public class Autor {
    @Id
    @Column(unique = true)
    private String nome;
    private int anoNascimento;
    private int anoFalecimento;

    public Autor(){

    }

    public Autor(AutorResultado author) {
        setNome(author.nome());
        setAnoNascimento(author.anoNascimento());
        setAnoFalecimento(author.anoFalescimento());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome.isBlank()){
            this.nome = "Autor não encontrado";
        } else {
            this.nome = nome;
        }
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public int getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(int anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                " | Ano de Nascimento: " + anoNascimento +
                " | Ano de Falecimento" + anoFalecimento;
    }
}
