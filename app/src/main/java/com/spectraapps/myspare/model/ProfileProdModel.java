package com.spectraapps.myspare.model;

import java.util.List;

/**
 * Created by MahmoudAyman on 06/03/2018.
 */

public class ProfileProdModel {

    /**
     * data : [{"pid":"90","productName":"spareparttt","productPrice":"2000","productNumber":"200","currency":"دينار اماراتي","mobile":"0","image1":"http://myspare.net/public/upload/1520256835image1.png","image2":null,"image3":null,"id":"42","name":"","date":"2017","country":"germany","brand":"audi","model":"S5","isFavorite":"false"},{"pid":"89","productName":"lpl","productPrice":"2000","productNumber":"2","currency":"ريال قطرى","mobile":"1234567890","image1":"1520256063onejpg","image2":"1520256063twojpg","image3":"1520256063threejpg","id":"42","name":"nashwaa","date":"1990","country":"australia","brand":"acura","model":"Acura TSX","isFavorite":"false"}]
     * status : {"type":"success","title":"Successfull request"}
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
         * pid : 90
         * productName : spareparttt
         * productPrice : 2000
         * productNumber : 200
         * currency : دينار اماراتي
         * mobile : 0
         * image1 : http://myspare.net/public/upload/1520256835image1.png
         * image2 : null
         * image3 : null
         * id : 42
         * name :
         * date : 2017
         * country : germany
         * brand : audi
         * model : S5
         * isFavorite : false
         */

        private String pid;
        private String productName;
        private String productPrice;
        private String productNumber;
        private String currency;
        private String mobile;
        private String image1;
        private Object image2;
        private Object image3;
        private String id;
        private String name;
        private String date;
        private String country;
        private String brand;
        private String model;
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

        public Object getImage2() {
            return image2;
        }

        public void setImage2(Object image2) {
            this.image2 = image2;
        }

        public Object getImage3() {
            return image3;
        }

        public void setImage3(Object image3) {
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

        public String getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(String isFavorite) {
            this.isFavorite = isFavorite;
        }
    }
}
