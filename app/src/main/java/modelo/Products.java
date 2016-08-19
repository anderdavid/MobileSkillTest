package modelo;

import java.util.ArrayList;

/**
 * Created by Anderdavid on 13/08/2016.
 */
public class Products {

            private String createdAt;
            private String imageURL;
            private String name;
            private String objectId;
            private String price;
            private String quantity;
            private String updatedAt;
            private ArrayList<Items>itemsList;

    private class Items{
        private String nombre;
        private String descripcion;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Items(String nombre, String descripcion) {
            this.nombre = nombre;
            this.descripcion = descripcion;


        }
    }


    public Products(String createdAt, String imageURL, String name, String objectId, String price, String quantity, String updatedAt) {
        this.createdAt = createdAt;
        this.imageURL = imageURL;
        this.name = name;
        this.objectId = objectId;
        this.price = price;
        this.quantity = quantity;
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Products{" +
                "createdAt='" + createdAt + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", name='" + name + '\'' +
                ", objectId='" + objectId + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
