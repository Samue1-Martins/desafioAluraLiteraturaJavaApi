package br.com.alura.literatura.main;

import br.com.alura.literatura.model.*;
import br.com.alura.literatura.repository.LiteratureRepository;
import br.com.alura.literatura.service.ConverteData;
import br.com.alura.literatura.service.RequestGutendexAPI;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

//        searchBooks();

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
            }

        }
//        getDbDataBooks();
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
//        System.out.println("JSON recebido da API:\n" + json);
        DataApiResponse dataApiResponse = converter.getData(json, DataApiResponse.class);
        return dataApiResponse;
    }

    private void searchBooks(){
        DataApiResponse dataApiResponse = getBooksDataApi();
        List<DataLiterature> listBooks = dataApiResponse.results();

        for (DataLiterature dataLiterature : listBooks) {

            Literature bookLiterature = new Literature(dataLiterature);
            System.out.println(bookLiterature);

            List<Authors> authorsList = new ArrayList<>();

            for (DataAuthors dataAuthors : dataLiterature.authors()){
                Authors authors = new Authors(dataAuthors);
                authorsList.add(authors);
            }

            bookLiterature.setAuthorsList(authorsList);
//            literatureRepository.save(bookLiterature);
        }
    }



    private void getDbDataBooks(){
        literatureBooks = literatureRepository.findAll();
        literatureBooks.stream()
                .forEach(System.out::println);
    }
}
