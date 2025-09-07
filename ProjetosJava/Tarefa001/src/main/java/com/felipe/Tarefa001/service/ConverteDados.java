package com.felipe.Tarefa001.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipe.Tarefa001.model.Tarefa;

public class ConverteDados implements IConverteDados {

    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> String gravarDados(T tarefa) {
        try {
            return mapper.writeValueAsString(tarefa);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
