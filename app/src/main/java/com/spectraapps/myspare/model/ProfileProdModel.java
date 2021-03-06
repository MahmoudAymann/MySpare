package com.spectraapps.myspare.model;

import java.util.List;

/**
 * Created by MahmoudAyman on 06/03/2018.
 */

public class ProfileProdModel {

    /**
     * data : [{"pid":"182","productName":"golden tires","productPrice":"2500","productNumber":"122133","currency":"Egyptian Pound",
     * "mobile":"","image1":"http://myspare.net/public/upload/1520931444tire1.jpg","image2":"http://myspare.net/public/upload/1520931444tire2.jpg",
     * "image3":"",
     * "id":"1","name":"","date":"2018","country":"turkey","brand":"bmw","model":"3 series",
     * "image":"http://myspare.net/public/upload/default.jpg","isFavorite":"false"}]
     * status : {"type":"success","title":"Successful request"}
     */

    private StatusBean status;
    private List<DataBean> data;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class StatusBean {
        /**
         * type : success
         * title : Successfull request
         */

        private String type;
        private String title;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class DataBean {

        /**
         * pid : 182
         * productName : golden tires
         * productPrice : 2500
         * productNumber : 122133
         * currency : Egyptian Pound
         * mobile :
         * image1 : http://myspare.net/public/upload/1520931444tire1.jpg
         * image2 : http://myspare.net/public/upload/1520931444tire2.jpg
         * image3 :
         * id : 1
         * name :
         * date : 2018
         * country : turkey
         * brand : bmw
         * model : 3 series
         * image : http://myspare.net/public/upload/default.jpg
         * isFavorite : false
         */

        private String pid;
        private String productName;
        private String productPrice;
        private String productNumber;
        private String currency;
        private String mobile;
        private String image1;
        private String image2;
        private String image3;
        private String id;
        private String name;
        private String date;
        private String country;
        private String brand;
        private String model;
        private String image;
        private String isFavorite;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getImage3() {
            return image3;
        }

        public void setImage3(String image3) {
            this.image3 = image3;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(String isFavorite) {
            this.isFavorite = isFavorite;
        }
    }
}
