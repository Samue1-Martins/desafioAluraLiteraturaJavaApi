package br.com.alura.literatura.main;

import br.com.alura.literatura.model.DataApiResponse;
import br.com.alura.literatura.model.DataLiterature;
import br.com.alura.literatura.model.Literature;
import br.com.alura.literatura.repository.LiteratureRepository;
import br.com.alura.literatura.service.ConverteData;
import br.com.alura.literatura.service.RequestGutendexAPI;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner reader = new Scanner(System.in);
    private RequestGutendexAPI requestGutendexAPI =  new RequestGutendexAPI();
    private final String addres = "http://gutendex.com/books/?search=dom%20casmurro";
    private ConverteData converter = new ConverteData();
    private List<DataApiResponse> literatureBooks = new ArrayList<DataApiResponse>();

    private ObjectMapper mapper = new ObjectMapper();


    private LiteratureRepository literatureRepository;

    public Main(LiteratureRepository literatureRepository) {
        this.literatureRepository = literatureRepository;
    }

    public void showMain() {
        searchBooks();
    }

    private void searchBooks(){
        DataApiResponse dataApiResponse = getDataBooks();
        Literature literature = new Literature(dataApiResponse);
        literatureBooks.add(dataApiResponse);
        System.out.println(literatureBooks);
    }

    private DataApiResponse getDataBooks(){
        System.out.println("Digite o nome do livro para busca");
        var nameBook = reader.nextLine();
        var json = requestGutendexAPI.getData(addres);
        System.out.println("JSON recebido da API:\n" + json);
        DataApiResponse dataApiResponse = converter.getData(json, DataApiResponse.class);
        System.out.println();
        return dataApiResponse;
    }
}
