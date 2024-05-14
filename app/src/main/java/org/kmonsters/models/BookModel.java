package org.kmonsters.models;



/*
 * this class is responsible for creating Book model which represents it
 * */
public class BookModel {
    private String name;
    private String url;
    private int price;

    public BookModel(String name, String url, Integer price) {
        this.name = name;
        this.url = url;
        this.price=price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}