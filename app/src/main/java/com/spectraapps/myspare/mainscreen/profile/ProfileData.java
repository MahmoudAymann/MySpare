package com.spectraapps.myspare.mainscreen.profile;

/**
 * Created by MahmoudAyman on 04/01/2018.
 */

public class ProfileData {
    private String name;
    private String image;

    public ProfileData() {
    }

    public ProfileData(String name , String image) {
        this.name = name;
        this.image = image;
    }

    public ProfileData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
