package cz.jandys.demo.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONProducer {
    JSONObject jsonObject = new JSONObject();


    public void add(String key, String value){
        jsonObject.put(key,value);
    }

    public void add(String key,JSONObject obj2){
        jsonObject.put(key,obj2);
    }

    public void add(String key, JSONArray array){
        jsonObject.put(key,array);
    }

    @Override
    public String toString() {
        return this.jsonObject.toJSONString();
    }

    public JSONObject getObject() {
        return this.jsonObject;
    }

    public String getValue(String key){
        return jsonObject.get(key).toString();
    }

    public JSONProducer fromBody(String s){
        try {
            this.jsonObject = (JSONObject) new JSONParser().parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }
}
