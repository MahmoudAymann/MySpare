package com.spectraapps.myspare.model;

import java.util.List;

/**
 * Created by MahmoudAyman on 07/03/2018.
 */

public class FavouriteModel {
    /**
     * status : {"type":"success","title":"Successfull request"}
     *  data : [{"pid":"96","productName":"3","productPrice":"23","productNumber":"23332",
     * "currency":"Qatar Riyal","mobile":"123456789",
     * "image1":"http://myspare.net/public/upload/1520423164onejpg",
     * "image2":"http://myspare.net/public/upload/1520423164twojpg",
     * "image3":"http://myspare.net/public/upload/1520423164threejpg",
     * "date":"1990","country":"australia","category":"External Body","brand":"audi",
     * "model":"100","user_name":"Mahmoyd Ayman ","isFavorite":"true"}]
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
         * pid : 96
         * productName : 3
         * productPrice : 23
         * productNumber : 23332
         * currency : Qatar Riyal
         * mobile : 123456789
         * image1 : http://myspare.net/public/upload/1520423164onejpg
         * image2 : http://myspare.net/public/upload/1520423164twojpg
         * image3 : http://myspare.net/public/upload/1520423164threejpg
         * date : 1990
         * country : australia
         * category : External Body
         * brand : audi
         * model : 100
         * user_name : Mahmoyd Ayman
         * isFavorite : true
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
        private String date;
        private String country;
        private String category;
        private String brand;
        private String model;
        private String user_name;
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(String isFavorite) {
            this.isFavorite = isFavorite;
        }
    }
}
