package com.solaredge.restaurantreservations.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    private final static ObjectMapper mapper = new ObjectMapper();

    public static String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("failed to serialize object", e);
        }
    }
}
