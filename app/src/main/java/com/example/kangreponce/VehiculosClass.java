package com.example.kangreponce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class VehiculosClass {

    private Result result;


    public VehiculosClass() {
    }


    public Result JsonParse(String response){
        Gson gson = new GsonBuilder().create();
        Result result = gson.fromJson(response, Result.class);
        return result;
    }

}
