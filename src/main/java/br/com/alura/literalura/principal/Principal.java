package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.services.ConsumoApi;

import java.util.Scanner;

public class Principal {

    private final Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConsumoApi consumo = new ConsumoApi();

    boolean procurandoLivros;

    public void exibeMenu(){

        var json = consumo.obterDados(ENDERECO);
        System.out.println("""
                        Bem vindo ao literalura
                        Uma API que utiliza o GUTENDEX para procurar livros\ne guardá-los em um banco de dados
                """);

        while (true){
            System.out.println("""
                                Escolha a opção desejada:
                                1 - Buscar e registrar livros pelo título (Gutendex API)
                                
                    """);
            // Comentado para ser adicionado conforme avanço
//            2 - Listar livros registrados
//            3 - Listar autores registrados
//            4 - Listar autores vivos em um determinado ano
//            5 - Listar livros em um determinado idioma
//
//            0 - Sair
            String opcao = leitura.nextLine();

            switch (opcao) {
                case "1" : searchBookByTitle();
                    break;
                case "0" :
                    System.out.println("Aplicação feita por Caio Zanchetta Reis");
                    System.out.println("Obrigado por usar esta aplicação!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void searchBookByTitle() {
        procurandoLivros = true;
        while (procurandoLivros) {

            System.out.println("Digite o nome do livro");
            String livroProcurar = leitura.nextLine();
            String enderecoTitulo = ENDERECO + livroProcurar.replace(" ", "+").toLowerCase();
            String livroJson = consumo.obterDados(enderecoTitulo);

//            if (livroJson.){
//
//            }

            System.out.println(livroJson);
        }
    }

}
