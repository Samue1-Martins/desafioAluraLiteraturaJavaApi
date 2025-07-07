package br.com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataLiterature(
        String title,
        List<DataAuthors> authors,
        List<String> languages,
        @JsonAlias("download_count") Integer downloadCount){
}
