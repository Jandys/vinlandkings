package cz.jandys.demo.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
}
