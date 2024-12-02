package com.example.comprasonline;

public class Product {
    private String name; // Nome do produto
    private String price; // Preço do produto
    private Object image; // Pode ser um URI ou um ID de recurso

    public Product(String name, String price, Object image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public Object getImage() { // Método para obter a imagem
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(Object image) {
        this.image = image;
    }
}
