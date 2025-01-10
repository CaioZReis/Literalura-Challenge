package br.com.alura.literalura.principal;

import br.com.alura.literalura.dto.LivroResultados;
import br.com.alura.literalura.dto.RespostaDto;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.repositorio.RepositorioAutor;
import br.com.alura.literalura.repositorio.RepositorioLivro;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.services.ConsumoApi;
import br.com.alura.literalura.services.ConverteDado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private RepositorioAutor repositorioAutor;
    private RepositorioLivro repositorioLivro;
    private final Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConsumoApi consumo = new ConsumoApi();
    private final ConverteDado conversor = new ConverteDado();

    boolean procurandoLivros;

    public Principal(RepositorioAutor repositorioAutor, RepositorioLivro repositorioLivro) {
        this.repositorioAutor = repositorioAutor;
        this.repositorioLivro = repositorioLivro;
    }

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
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    
                    0 - Sair
                    """);
            // Comentado para ser adicionado conforme avanço
//            5 - Listar livros em um determinado idioma

            String opcao = leitura.nextLine();

            switch (opcao) {
                case "1" : procurarApi();
                    break;
                case "2":
                    procurarLivro();
                    System.out.println("Aperte enter para voltar ao menu...");
                    leitura.nextLine();
                    break;
                case "3":
                    procurarAutores();
                    System.out.println("Aperte enter para voltar ao menu...");
                    leitura.nextLine();
                    break;
                case "4":
                    procurarAutoresPorAno();
                    System.out.println("Aperte enter para voltar ao menu...");
                    leitura.nextLine();
                    leitura.nextLine();
                    break;
                case "5":
                    procurarPorLingua();
                    System.out.println("Aperte enter para voltar ao menu...");
                    leitura.nextLine();
                    leitura.nextLine();
                    break;
                case "0" :
                    System.out.println("\nAplicação feita por Caio Zanchetta Reis\nObrigado por usar esta aplicação!\n\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void procurarApi() {
        while (true) {

            System.out.println("\nDigite o nome do livro ou aperte 0 para voltar");
            String livroProcurar = leitura.nextLine();
            if (livroProcurar.equals("0")){
                return;
            }
            String enderecoTitulo = ENDERECO + livroProcurar.replace(" ", "+").toLowerCase();
            String livroJson = consumo.obterDados(enderecoTitulo);

            RespostaDto livroProcurado = conversor.obterDados(livroJson, RespostaDto.class);


            if (livroProcurado.quantidade() == 0){
                System.out.println("Livro não encontrado!!");
            } else if (livroProcurado.quantidade() == 1) {
                LivroResultados livro = livroProcurado.livroResultadosApi().getFirst();
                verificarLivro(livro);
            } else if (livroProcurado.quantidade() > 1){
                System.out.printf("Foram encontrados %s livros por favor seja mais específico no título", livroProcurado.quantidade());
            }
        }
    }

    private void verificarLivro(LivroResultados livroEncontrado){
        System.out.println(livroEncontrado + "\nEste livro será adicionado ao banco de dados, o livro está correto? (S/N)");
        String verificadorLivro = leitura.nextLine().toLowerCase();
        if(verificadorLivro.equals("s")){
            registrarLivro(livroEncontrado);
        } else if (verificadorLivro.equals("n")){
            procurarApi();
        } else {
            System.out.println("Opção inválida");
            verificarLivro(livroEncontrado);
        }
    }

    private void registrarLivro(LivroResultados livroEncontrado){
        if(!estaRegistrado(livroEncontrado)){
            Livro novoLivro;
            if (!livroEncontrado.autores().getFirst().nome().equals("Autor não encontrado")){
                Optional<Autor> autorBanco = repositorioAutor.findAutorByNome(livroEncontrado.autores().getFirst().nome());
                if (autorBanco.isPresent()) {
                    novoLivro = new Livro(livroEncontrado, autorBanco.get());
                    repositorioLivro.save(novoLivro);
                    System.out.println("Livro registrado com sucesso e Autor já existente!");
                } else {
                    Autor novoAutor = new Autor(livroEncontrado.autores().getFirst());
                    repositorioAutor.save(novoAutor);

                    autorBanco = repositorioAutor.findAutorByNome(novoAutor.getNome());
                    novoLivro = new Livro(livroEncontrado, autorBanco.get());
                    repositorioLivro.save(novoLivro);
                    System.out.println("Livro e Autor registrado com sucesso!");
                }
            } else {
                System.out.println("Livro está sem Autor quer adicionar mesmo assim? (S/N)");
                String verificadorAutor = leitura.nextLine();
                if(verificadorAutor.equals("s")){
                    novoLivro = new Livro(livroEncontrado);
                    repositorioLivro.save(novoLivro);
                } else if (verificadorAutor.equals("n")){
                    procurarApi();
                } else {
                    System.out.println("Opção inválida");
                    registrarLivro(livroEncontrado);
                }
            }
        } else {
            System.out.println("Livro e Autor já existem no banco de dados!");
            procurarApi();
        }
    }

    private boolean estaRegistrado(LivroResultados livroEncontrado){
        Optional<Livro> livroSalvo = repositorioLivro.findBookByTitulo(livroEncontrado.titulo());

        if (livroSalvo.isPresent()){
            System.out.println("O livro já existe\nVoltando para a pesquisa...");
            procurarApi();
            return true;
        } else {
            return false;
        }
    }

    private void procurarLivro(){
        List<Livro> livrosTabela = repositorioLivro.findAll();

        if(livrosTabela.isEmpty()) {
            System.out.println("Nenhum livro registrado");
        } else {
            System.out.println("Livro(s) Procurado(s)");
            livrosTabela.forEach(System.out::println);
        }
    }

    private void procurarAutores(){
        List<Autor> AutoresTabela = repositorioAutor.findAll();

        if(AutoresTabela.isEmpty()) {
            System.out.println("Nenhum Autor registrado");
        } else {
            System.out.println("Autor(es) Encontrado(s)");
            AutoresTabela.forEach(System.out::println);
        }
    }

    private void procurarAutoresPorAno(){
        try {
            int anoAtual = LocalDate.now().getYear();
            System.out.println("Digite o ano:");
            int anoProcura = (leitura.nextInt());
            if (anoAtual < anoProcura){
                System.out.println("Não dá para procurar no futuro...");
                leitura.nextLine();
            } else {
                List<Autor> autoresVivos = repositorioAutor.encontrarAutorPorAno(anoProcura);
                if (!autoresVivos.isEmpty()){
                    System.out.printf("Autores vivo no período %s\n", anoProcura);
                    autoresVivos.forEach(System.out::println);
                } else {
                    System.out.printf("Não foi encontrado autores vivos no período %s\n", anoProcura);
                }
            }
        } catch (NumberFormatException e){
            System.out.println("Digite apenas números, sem letras ou símbolos...");
            leitura.nextLine();
        }
    }

    private void procurarPorLingua(){

    }

}
