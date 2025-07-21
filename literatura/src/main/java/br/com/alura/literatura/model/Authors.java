package br.com.alura.literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Authors {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birthYear;
    private Integer deathYear;

    @ManyToOne
    private Literature literature;

    public Authors(){}

    public Authors(DataAuthors dataAuthors){
        this.name = dataAuthors.name();
        this.birthYear = dataAuthors.birthYear();
        this.deathYear = dataAuthors.deathYear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Literature getLiterature() {
        return literature;
    }

    public void setLiterature(Literature literature) {
        this.literature = literature;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "--------- BUSCANDO LIVRO POR TITULO ---------" + "\n" +
                "'" + name + '\'' +
                ", birthYear = " + birthYear +
                ", deathYear = " + deathYear +
                '}';
    }
}
