package br.com.alura.literatura.service;

public interface IConverterData {
    <T> T getData(String json, Class<T> classes);
}
