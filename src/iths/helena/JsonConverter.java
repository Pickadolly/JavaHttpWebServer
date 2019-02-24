package iths.helena;

import com.google.gson.Gson;

public class JsonConverter {
    private Gson gson = new Gson();
    private Person p;

    public JsonConverter(Person p) {
        this.p = p;
    }

    public String personToJson() {
        return gson.toJson(p);
    }
}