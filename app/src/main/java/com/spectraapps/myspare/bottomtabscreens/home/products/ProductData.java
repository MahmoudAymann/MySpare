package com.spectraapps.myspare.bottomtabscreens.home.products;

/**
 * Created by MahmoudAyman on 13/01/2018.
 */

public class ProductData {

    private int image;
    private String name;
    private String price;

    public ProductData() {
    }

    public ProductData(int image, String name, String price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
}
