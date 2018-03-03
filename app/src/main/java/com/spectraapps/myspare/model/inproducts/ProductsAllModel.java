package com.spectraapps.myspare.model.inproducts;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MahmoudAyman on 26/02/2018.
 */

public class ProductsAllModel implements Serializable {

    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : [{"pid":"11","productName":"ت ت ت","productPrice":"55555",
     * "productNumber":"5555","currency":"Qatar Riyal","mobile":"123456789",
     * "image1":"http://myspare.net/public/upload/image1.png","image2":"http://myspare.net/public/upload/image2.png",
     * "image3":"http://myspare.net/public/upload/image3.png","id":"19","name":"amr","date":"2018",
     * "country":"Qatar","brand":"aston martin","model":"Acura MDX"}]
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
         * pid : 11
         * productName : mahmoudProduct
         * productPrice : 55555
         * productNumber : 5555
         * currency : Qatar Riyal
         * mobile : 123456789
         * image1 : http://myspare.net/public/upload/image1.png
         * image2 : http://myspare.net/public/upload/image2.png
         * image3 : null
         * id : 19
         * name : amr
         * date : 2018
         * country : Qatar
         * brand : aston martin
         * model : Acura MDX
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
    }
}
