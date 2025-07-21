package br.com.alura.literatura.main;

import br.com.alura.literatura.model.*;
import br.com.alura.literatura.repository.LiteratureRepository;
import br.com.alura.literatura.service.ConverteData;
import br.com.alura.literatura.service.RequestGutendexAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final Scanner reader = new Scanner(System.in);
    private final RequestGutendexAPI requestGutendexAPI =  new RequestGutendexAPI();
    private final String urlApi = "http://gutendex.com/books/?search=";
    private final ConverteData converter = new ConverteData();
    private final LiteratureRepository literatureRepository;
    private  List<Literature> literatureBooks = new ArrayList<>();

    public Main(LiteratureRepository literatureRepository) {
        this.literatureRepository = literatureRepository;
    }

    public void principal() {

        var option = -1;
        while (option != 0){
            showMain();

            option = reader.nextInt();
            reader.nextLine();
            switch (option){
                case 1:
                    searchBooks();
                    break;
                case 2:
                    getDbDataBooks();
                    break;
                case 3:
                    getDbDataAuthors();
                    break;
                case 4:
                    searchByYearAliveDb();
                    break;
                case 5:
                    searchBookByLanguage();
                    break;
            }
        }
    }

    private void showMain(){
        System.out.println(
                """
                Escolha o número de sua opção:
                1 - buscar livro pelo titulo
                2 - listar livros registrados
                3 - listar autores registrados
                4 - listar autores vivos entre um determinado ano
                5 - listar livros em um determinado idioma
                0 - sair
                """);
    }

    private DataApiResponse getBooksDataApi(){
        System.out.println("Digite o nome do livro para busca: ");
        var nameBook = reader.nextLine();
        var json = requestGutendexAPI.getData(urlApi + nameBook.replace(" ", "%20"));
        System.out.println("JSON recebido da API:\n" + json);
        DataApiResponse dataApiResponse = converter.getData(json, DataApiResponse.class);
        return dataApiResponse;
    }

    private void searchBooks(){
        DataApiResponse dataApiResponse = getBooksDataApi();

//        verifyBooksDb(dataApiResponse.results().getFirst().title());

        if (dataApiResponse.count() == 0) {
            System.out.println("""
                    --------------------------------
                    Livro não encontrado
                    --------------------------------
                    """);
        } else {

            try {
                List<DataLiterature> listBooks = dataApiResponse.results();

                for (int i = 0; i < listBooks.size() ; i++) {

                    for (DataLiterature dataLiterature : listBooks) {

                        Literature bookLiterature = new Literature(dataLiterature);

                        List<Authors> authorsList = new ArrayList<>();

                        for (DataAuthors dataAuthors : dataLiterature.authors()){
                            Authors authors = new Authors(dataAuthors);
                            authorsList.add(authors);
                        }

                        bookLiterature.setAuthorsList(authorsList);
                        System.out.println(bookLiterature);
                    literatureRepository.save(bookLiterature);
                    }
                }
            } catch (RuntimeException e) {
                System.out.println("Não foi possivel salvar o livro, error: " + e);
                throw new RuntimeException(e);
            }
        }
    }

    private void verifyBooksDb(String nameBook){
        var book = literatureRepository.findByTitleContainingIgnoreCase(nameBook);
        if(book.isPresent()) {
            System.out.println("Dados livros " + book.get());
        } else {
            System.out.println("Livro não encontrado!");
        }
    }

    private void getDbDataBooks(){
        literatureBooks = literatureRepository.findAll();
        literatureBooks.forEach(l->
                System.out.println( "--------- LIVROS REGISTRADOS ---------" + "\n" +
                "Titulo: " + l.getTitle()+ "\n" +
                "Autor: " + l.getAuthorsList().getFirst().getName() + "\n" +
                "Idioma: " + l.getLanguages().getFirst() + "\n" +
                "Número de downloads: " + l.getDownloadCount()+ "\n" +
                "--------------------------------------"
        ) );
    }

    private void getDbDataAuthors(){
        literatureBooks = literatureRepository.findAll();
        literatureBooks.forEach(l ->
                System.out.println( "---------------------" + "\n"
                         + "Autores registrados" + "\n" +
                        "---------------------" + "\n" +
                        "Nome: " + l.getAuthorsList().getFirst().getName() + "\n" +
                        "Ano de nascimento: " + l.getAuthorsList().getFirst().getBirthYear() + "\n" +
                        "Ano de falecimento: " + l.getAuthorsList().getFirst().getDeathYear() + "\n" +
                        "livros: " + l.getTitle() + "\n" +
                        "---------------------"
                ));
    }

    private void searchByYearAliveDb(){

        System.out.println("Adicione o ano para buscar autores vivos em determinado ano");

        var searchYear = reader.nextInt();

        reader.nextLine();

        List<Literature> authorAlive = literatureRepository.findByBirthYearAndDeathYear(searchYear);

        if(authorAlive.isEmpty()) {
            System.out.println("Não há autores vivos nestes determinados anos!");
        } else {
            authorAlive.forEach(a ->
                    System.out.println("---------------------" + "\n"
                            + "Autores registrados" + "\n" +
                            "---------------------" + "\n" +
                            "Nome: " + a.getTitle() + "\n" +
                            "Ano de nascimento: " + a.getAuthorsList().getFirst().getBirthYear() + "\n" +
                            "Ano de falecimento: " + a.getAuthorsList().getFirst().getDeathYear() + "\n" +
                            "livros: " + a.getTitle() + "\n" +
                            "---------------------"));
        }
    }

    private void searchBookByLanguage(){

        System.out.println("""
                Insira o idioma para realizar a busca:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);

        var searchBooksByLanguage = reader.nextLine();

        List<Literature> findBookByLanguage = literatureRepository.findByBookLanguage(searchBooksByLanguage);

        if(findBookByLanguage.isEmpty()) {
            System.out.println("Não há livros neste idioma!");
        } else {
            findBookByLanguage.forEach(l -> System.out.println( "--------------------------" + "\n"
                + "Listando livros por Idioma" + "\n" +
                "--------------------------" + "\n" +
                "Titulo: " + l.getTitle()+ "\n" +
                "Autor: " + l.getAuthorsList().getFirst().getName() + "\n" +
                "Idioma: " + l.getLanguages().getFirst() + "\n" +
                "Número de downloads: " + l.getDownloadCount()+ "\n" +
                "--------------------------"));
        }
    }
}
