package com.example.kangreponce;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Desarializer implements JsonDeserializer<VehiculosClass> {

    @Override
    public VehiculosClass deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String _id = json.getAsJsonObject().get("result").getAsJsonArray().get(0).getAsJsonObject().get("_id").getAsString();
        String Marca = json.getAsJsonObject().get("result").getAsJsonArray().get(0).getAsJsonObject().get("Marca").getAsString();
        String Modelo = json.getAsJsonObject().get("result").getAsJsonArray().get(0).getAsJsonObject().get("Modelo").getAsString();
        String Color = json.getAsJsonObject().get("result").getAsJsonArray().get(0).getAsJsonObject().get("Color").getAsString();
        int Precio = json.getAsJsonObject().get("result").getAsJsonArray().get(0).getAsJsonObject().get("Precio").getAsInt();
        String Year = json.getAsJsonObject().get("result").getAsJsonArray().get(0).getAsJsonObject().get("Year").getAsString();
        VehiculosClass vehiculosClass = new VehiculosClass(_id, Marca, Modelo, Color, Precio, Year);
        return vehiculosClass;
    }
}
