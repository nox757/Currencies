package com.chibisovap.parser;

import com.chibisovap.models.ApiResponse;
import com.chibisovap.models.RateObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonParser {

    private final static Gson gson = new GsonBuilder().registerTypeAdapter(RateObject.class, new RatesDeserializer()).create();

    public static ApiResponse parseJsonToApiResponse(String in) {
        return gson.fromJson(in, ApiResponse.class);
    }
}
