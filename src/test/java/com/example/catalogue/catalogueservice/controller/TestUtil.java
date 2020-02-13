package com.example.catalogue.catalogueservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

    public static String asJsonString(final Object object) throws JsonProcessingException {

        return new ObjectMapper().writeValueAsString(object);

    }

    public static <T> T asObject(String json, Class<T> clazz) throws JsonProcessingException {

        return new ObjectMapper().readValue(json, clazz);
    }

}
