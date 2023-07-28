package com.example.kangreponce;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Desarializer implements JsonDeserializer<VehiculosClass> {

    @Override
    public VehiculosClass deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
       return null;
    }
}
