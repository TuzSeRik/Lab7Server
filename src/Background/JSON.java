package Background;

import com.google.gson.Gson;

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