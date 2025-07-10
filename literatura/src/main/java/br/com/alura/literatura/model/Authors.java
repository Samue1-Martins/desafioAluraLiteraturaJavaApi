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

    public Integer getDeath_year() {
        return deathYear;
    }

    public void setDeath_year(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public Integer getBirth_year() {
        return birthYear;
    }

    public void setBirth_year(Integer birthYear) {
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
        return "Authors{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                '}';
    }
}
