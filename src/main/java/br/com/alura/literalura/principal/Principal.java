package br.com.alura.literalura.principal;

import br.com.alura.literalura.dto.LivroResultados;
import br.com.alura.literalura.dto.RespostaDto;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repositorio.RepositorioAutor;
import br.com.alura.literalura.repositorio.RepositorioLivro;
import br.com.alura.literalura.services.ConsumoApi;
import br.com.alura.literalura.services.ConverteDado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private RepositorioAutor repositorioAutor;
    private RepositorioLivro repositorioLivro;
    private final Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConsumoApi consumo = new ConsumoApi();
    private final ConverteDado conversor = new ConverteDado();


    public Principal(RepositorioAutor repositorioAutor, RepositorioLivro repositorioLivro) {
        this.repositorioAutor = repositorioAutor;
        this.repositorioLivro = repositorioLivro;
    }

    public void exibeMenu(){

        System.out.println("""
                
                Bem vindo ao literalura
                Uma API que utiliza o GUTENDEX para procurar livro e guardá-los em um banco de dados
                """);

        while (true){
            System.out.println("""
                                    Menu Principal
                    1 - Buscar e registrar livros pelo título (Gutendex API)
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair
                    Escolha a opção desejada:""");
            
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
                    break;
                case "5":
                    procurarPorLingua();
                    System.out.println("Aperte enter para voltar ao menu...");
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
                //Fazer uma chamada recursiva para adicionar somente 1 livro
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
        }
    }

    private boolean estaRegistrado(LivroResultados livroEncontrado){
        Optional<Livro> livroSalvo = repositorioLivro.findBookByTitulo(livroEncontrado.titulo());

        if (livroSalvo.isPresent()){
            return true;
        } else {
            return false;
        }
    }

    private void procurarLivro(){
        List<Livro> livrosTabela = repositorioLivro.findLivros();

        if(livrosTabela.isEmpty()) {
            System.out.println("Nenhum livro registrado");
        } else {
            System.out.println("Livro(s) Procurado(s)");
            livrosTabela.forEach(System.out::println);
        }
    }

    private void procurarAutores() {
        List<Autor> autoresTabela = repositorioAutor.findAll();

        if (autoresTabela.isEmpty()) {
            System.out.println("Nenhum Autor registrado");
        } else {
            System.out.println("Autor(es) Encontrado(s)");
            autoresTabela.forEach(autor -> {
                List<Livro> livrosDoAutor = repositorioLivro.findLivrosByAutor(autor.getNome());

                // Obter os títulos dos livros como uma única string separada por vírgulas
                String livrosReferentes = livrosDoAutor.stream()
                        .map(Livro::getTitulo)
                        .collect(Collectors.joining(", "));

                System.out.printf(
                        "Nome: %s | Ano de Nascimento: %d | Ano de Falecimento: %d | Livros Referentes: %s%n",
                        autor.getNome(),
                        autor.getAnoNascimento(),
                        autor.getAnoFalecimento(),
                        livrosReferentes.isEmpty() ? "Nenhum livro encontrado" : livrosReferentes);
            });
        }
    }

    private void procurarAutoresPorAno() {
        try {
            int anoAtual = LocalDate.now().getYear();
            System.out.println("Digite o ano:");
            String anoProcura = leitura.nextLine();
            if (anoAtual < Integer.parseInt(anoProcura)){
                System.out.println("Não dá para procurar no futuro...\nTente outra data");
                leitura.nextLine();
            } else {
                List<Autor> autoresVivos = repositorioAutor.encontrarAutorPorAno(Integer.parseInt(anoProcura));
                if (!autoresVivos.isEmpty()){
                    System.out.printf("Autores vivo no ano de %s\n", anoProcura);
                    autoresVivos.forEach(System.out::println);
                } else {
                    System.out.printf("Não foi encontrado autores vivos no ano de %s\n", anoProcura);
                }
            }
        } catch (NumberFormatException e){
            System.out.println("Digite apenas números, sem letras ou símbolos...");
        }
    }

    private void procurarPorLingua(){
        List<String> linguasTabela = repositorioLivro.findDiferentesLinguas();

        if (linguasTabela.isEmpty()) {
            System.out.println("Nenhuma língua encontrada no banco de dados.");
        } else {
            System.out.println("\nLínguas disponíveis:\n");
            for (String lingua : linguasTabela) {
                String linguaTransformada = switch (lingua) {
                    case "pt" -> "Português";
                    case "en" -> "Inglês";
                    case "es" -> "Espanhol";
                    default -> "Lingua não mapeada";
                };
                System.out.println(lingua + " - " + linguaTransformada);
            }
            System.out.println("Escolha a idioma desejado: ");
            String linguaProcurar = leitura.nextLine();
            String linguaTransformada = switch (linguaProcurar) {
                case "pt" -> "Português";
                case "en" -> "Inglês";
                case "es" -> "Espanhol";
                default -> "'Não mapeado'";
            };
            List<Livro> livros = repositorioLivro.findLivrosByLingua(linguaProcurar);

            if (livros.isEmpty()) {
                System.out.println("Nenhum livro encontrado para o idioma: " + linguaTransformada);
            } else {
                System.out.println("Livros encontrados no idioma " + linguaTransformada + ":");
                livros.forEach(System.out::println);
            }
        }
    }
}
