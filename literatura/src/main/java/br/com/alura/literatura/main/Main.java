package br.com.alura.literatura.main;

import br.com.alura.literatura.model.*;
import br.com.alura.literatura.repository.LiteratureRepository;
import br.com.alura.literatura.service.ConverteData;
import br.com.alura.literatura.service.RequestGutendexAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner reader = new Scanner(System.in);
    private RequestGutendexAPI requestGutendexAPI =  new RequestGutendexAPI();
    private final String urlApi = "http://gutendex.com/books/?search=dom%20casmurro";
    private ConverteData converter = new ConverteData();
    private LiteratureRepository literatureRepository;

    private List<DataApiResponse> responseList = new ArrayList<>();
    private List<Literature> literatureBooks = new ArrayList<>();

    public Main(LiteratureRepository literatureRepository) {
        this.literatureRepository = literatureRepository;
    }

    public void showMain() {
//        searchBooks();
        getDataBooks();
    }

    private void searchBooks(){
        DataApiResponse dataApiResponse = getDataApi();
        List<DataLiterature> listBooks = dataApiResponse.results();

        System.out.println(listBooks);
        for (DataLiterature dataLiterature : listBooks) {

            Literature bookLiterature = new Literature(dataLiterature);
            System.out.println(bookLiterature);

            List<Authors> authorsList = new ArrayList<>();

            for (DataAuthors dataAuthors : dataLiterature.authors()){
                Authors authors = new Authors(dataAuthors);
                authorsList.add(authors);
            }

            bookLiterature.setAuthorsList(authorsList);
            System.out.println("Salvando livro");
            literatureRepository.save(bookLiterature);
        }
    }

    private DataApiResponse getDataApi(){
//        System.out.println("Digite o nome do livro para busca");
//        var nameBook = reader.nextLine();
        var json = requestGutendexAPI.getData(urlApi);
        System.out.println("JSON recebido da API:\n" + json);
        DataApiResponse dataApiResponse = converter.getData(json, DataApiResponse.class);
        return dataApiResponse;
    }

    private void getDataBooks(){
        literatureBooks = literatureRepository.findAll();
        literatureBooks.stream()
                .forEach(System.out::println);
    }
}
