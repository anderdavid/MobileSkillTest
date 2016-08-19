package modelo;

/**
 * Created by Anderdavid on 17/08/2016.
 */
public class Productos {

    public final static String NAME="name";
    public final static String PRICE ="price";
    public final static String QUANTITY="quantity";
    public final static String IMAGE_URL = "imageURL";
    public final static String OBJECT_ID ="objectId";

    String name;
    String price;
    String quantity;
    String photoId;
    String objectId;

    public Productos(String name, String price, String quantity, String photoId, String objectId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.photoId = photoId;
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
