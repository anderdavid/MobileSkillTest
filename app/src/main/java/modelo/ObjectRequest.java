package modelo;

import org.json.JSONObject;

/**
 * Created by Anderdavid on 18/08/2016.
 */
public class ObjectRequest {
    String method;
    String path;
    JSONObject body;

    public ObjectRequest(String path, JSONObject body) {
        this.method = "PUT";
        this.path = "/1/classes/products/"+path;
        this.body = body;
    }
}
