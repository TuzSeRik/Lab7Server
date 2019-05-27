package com.tuzserik.github.shorties.server.serializing;

import com.google.gson.Gson;
import com.tuzserik.github.shorties.server.background.Shorty;

public class JSON {
    private static Gson gson = new Gson();

    public static Shorty fromJSON(String string){
        return gson.fromJson(string, Shorty.class);
    }

    public static String toJSON(Shorty shorty){
        return gson.toJson(shorty);
    }
}
//+