package br.com.alura.literalura.services;

public interface IConverteDado {
    <T> T obterDados (String json, Class<T> tClass);
}
