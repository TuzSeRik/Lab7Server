package Server.Background;

import Server.Background.Shorty;
import com.google.gson.Gson;

public class JSON {
    private static Gson gson = new Gson();

    static Shorty fromJSON(String string){
        return gson.fromJson(string, Shorty.class);
    }

    public static String toJSON(Shorty shorty){
        return gson.toJson(shorty);
    }
}
//+