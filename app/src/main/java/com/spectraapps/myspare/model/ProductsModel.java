package com.spectraapps.myspare.model;

import java.util.List;

/**
 * Created by MahmoudAyman on 24/02/2018.
 */

public class ProductsModel {

    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : [{"pid":"2","productName":"external","productPrice":"2589","productNumber":"213654","currency":"LE","mobile":null,"image1":"http://myspare.net/api/uploadedimage/20170905_143808.jpg","image2":null,"image3":null,"id":"3","name":"","date":"2010","country":"saudi","brand":"infiniti","model":"Acura MDX","isFavorite":"false"},{"pid":"4","productName":"ahmed","productPrice":"2589","productNumber":"213654","currency":"2","mobile":null,"image1":"http://myspare.net/api/uploadedimage/20170905_143808.jpg","image2":"http://myspare.net/api/uploadedimage/20170921_011156.jpg","image3":"http://myspare.net/api/uploadedimage/20170921_011354.jpg","id":"3","name":"","date":"2010","country":"saudi","brand":"infiniti","model":"Acura MDX","isFavorite":"true"},{"pid":"7","productName":"ahmed","productPrice":"2589","productNumber":"213654","currency":"2","mobile":null,"image1":"http://myspare.net/api/uploadedimage/20170905_143808.jpg","image2":null,"image3":null,"id":"3","name":"","date":"2010","country":"saudi","brand":"infiniti","model":"Acura MDX","isFavorite":"false"}]
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
         * pid : 2
         * productName : external
         * productPrice : 2589
         * productNumber : 213654
         * currency : LE
         * mobile : null
         * image1 : http://myspare.net/api/uploadedimage/20170905_143808.jpg
         * image2 : null
         * image3 : null
         * id : 3
         * name :
         * date : 2010
         * country : saudi
         * brand : infiniti
         * model : Acura MDX
         * isFavorite : false
         */

        private String pid;
        private String productName;
        private String productPrice;
        private String productNumber;
        private String currency;
        private Object mobile;
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

        public Object getMobile() {
            return mobile;
        }

        public void setMobile(Object mobile) {
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
