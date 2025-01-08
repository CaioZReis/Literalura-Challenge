package br.com.alura.literalura.principal;

import br.com.alura.literalura.dto.RespostaDto;
import br.com.alura.literalura.services.ConsumoApi;
import br.com.alura.literalura.services.ConverteDado;

import java.util.Scanner;

public class Principal {

    private final Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConsumoApi consumo = new ConsumoApi();
    private final ConverteDado conversor = new ConverteDado();

    boolean procurandoLivros;

    public void exibeMenu(){

        var json = consumo.obterDados(ENDERECO);
        System.out.println("""
                Bem vindo ao literalura
                Uma API que utiliza o GUTENDEX para procurar livro e guardá-los em um banco de dados
                """);

        while (true){
            System.out.println("""
                    Escolha a opção desejada:
                    1 - Buscar e registrar livros pelo título (Gutendex API)
                    
                    0 - Sair
                    """);
            // Comentado para ser adicionado conforme avanço
//            2 - Listar livros registrados
//            3 - Listar autores registrados
//            4 - Listar autores vivos em um determinado ano
//            5 - Listar livros em um determinado idioma

            String opcao = leitura.nextLine();

            switch (opcao) {
                case "1" : procurarTitulo();
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

    private void procurarTitulo() {
        procurandoLivros = true;
        while (procurandoLivros) {

            System.out.println("\nDigite o nome do livro ou aperte 0 para voltar");
            String livroProcurar = leitura.nextLine();
            if (livroProcurar.equals("0")){
                procurandoLivros = false;
            }
            String enderecoTitulo = ENDERECO + livroProcurar.replace(" ", "+").toLowerCase();
            String livroJson = consumo.obterDados(enderecoTitulo);

            RespostaDto livroProcurado = conversor.obterDados(livroJson, RespostaDto.class);


            if (livroProcurado.quantidade() == 0){
                System.out.println("Livro não encontrado!!");
            } else if (livroProcurado.quantidade() == 1) {
                System.out.println(livroProcurado);
            }
//            System.out.println(livroJson);
        }
    }

}
