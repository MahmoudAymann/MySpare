package com.spectraapps.myspare.model;

import java.util.List;

/**
 * Created by MahmoudAyman on 07/03/2018.
 */

public class FavouriteModel {

    /**
     * status : {"type":"Failed","title":"error occured"}
     * data : [{"pid":"195","productName":"كرسي","productPrice":"1000","productNumber":"1234","currency":"Qatar Riyal","mobile":"01118031092","image1":"http://myspare.net/public/upload/1521474723.images.jpg","image2":"http://myspare.net/public/upload/1521474723.Cerato 2009-2013 Rear Bumper-800x800.jpg","image3":"","date":"1994","country":"china","category":"Internal Body","brand":"BMW","model":"503","name":"Ali Gaber","id":"74","image":"http://myspare.net/public/upload/default.jpg","isFavorite":"true"}]
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
         * type : Failed
         * title : error occured
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
         * pid : 195
         * productName : كرسي
         * productPrice : 1000
         * productNumber : 1234
         * currency : Qatar Riyal
         * mobile : 01118031092
         * image1 : http://myspare.net/public/upload/1521474723.images.jpg
         * image2 : http://myspare.net/public/upload/1521474723.Cerato 2009-2013 Rear Bumper-800x800.jpg
         * image3 :
         * date : 1994
         * country : china
         * category : Internal Body
         * brand : BMW
         * model : 503
         * name : Ali Gaber
         * id : 74
         * image : http://myspare.net/public/upload/default.jpg
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
        private String name;
        private String id;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
