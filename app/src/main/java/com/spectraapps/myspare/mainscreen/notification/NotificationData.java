package com.spectraapps.myspare.mainscreen.notification;

/**
 * Created by MahmoudAyman on 04/01/2018.
 */

public class NotificationData {

    private String image;
    private String date;
    private String text;

    public NotificationData() {
    }

    public NotificationData(String date, String text) {
        this.date = date;
        this.text = text;
    }

    public NotificationData(String image, String date, String text) {
        this.image = image;
        this.date = date;
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
