package br.com.alura.literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "literature")
public class Literature {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany(mappedBy = "literature", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authors> authorsList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "language")
    @Column(name = "language_book")
    private List<String> languages = new ArrayList<>();

    private int downloadCount;

    public Literature(){}

    public Literature(DataLiterature dataLiterature) {
        this.title = dataLiterature.title();
        this.downloadCount = dataLiterature.downloadCount();
        this.languages = dataLiterature.languages();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Authors> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<Authors> authorsList) {
        authorsList.forEach(a -> a.setLiterature(this));
        this.authorsList = authorsList;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "--------- BUSCANDO LIVRO POR TITULO ---------" +
                "\n" +
                "Titulo: " + title + '\n' +
                "Autor:  " + authorsList.getFirst().getName() + "\n"+
                "Idioma: "+ languages.getFirst() + "\n" +
                "Número de downloads: " + downloadCount +
                "\n" +
                "--------------------------------------------" + "\n" +
                "\n" +
                "--------------------------------------------" ;
    }
}
