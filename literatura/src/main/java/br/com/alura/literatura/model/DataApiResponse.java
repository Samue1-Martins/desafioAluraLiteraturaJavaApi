package br.com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataApiResponse(
        Integer count,
        List<DataLiterature> results) {
}