package com.spectraapps.myspare.mainscreen.home;

/**
 * Created by MahmoudAyman on 03/01/2018.
 */

public class HomeData {


    private String image;
    private String name;

    public HomeData() {
    }

    HomeData(String name) {
        this.name = name;
    }

    public HomeData(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }
}
