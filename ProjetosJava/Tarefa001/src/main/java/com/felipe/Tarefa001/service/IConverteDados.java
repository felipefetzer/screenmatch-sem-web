package com.felipe.Tarefa001.service;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> classe);

    <T> String gravarDados(T tarefa);
}
