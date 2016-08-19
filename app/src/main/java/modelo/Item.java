package modelo;

/**
 * Created by Anderdavid on 18/08/2016.
 */
public class Item {

    String build;
    public String key;
    public String value;

    public Item(String key, String value) {
        this.key = key;
        this.value = value;
        build ="";
    }

    public String getItems(String items){

        StringBuilder jItems = new StringBuilder(items);
        String json = "{\""+key+"\":\""+value+"\"}";
        jItems.append(json);

        return jItems.toString();
    }
}
