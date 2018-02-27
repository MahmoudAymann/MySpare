package com.spectraapps.myspare.model;

/**
 * Created by MahmoudAyman on 24/02/2018.
 */

public class AddModel {

    /**
     * status : {"type":"success","title":" insertion with uploading has been completed "}
     * data : {"id":"12","name":"amnayaasd","number":"213654","manufacturingCountry":"2","brand":"1","model":"2","category":"6","price":"2589","currency":"LE","image1":"http://myspare.net/public/upload/contact_center.jpg","image2":"http://myspare.net/public/upload/contact_center.jpg","image3":"http://myspare.net/public/upload/contact_center.jpg","country":"6","date":"2019"}
     */

    private StatusBean status;
    private DataBean data;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class StatusBean {
        /**
         * type : success
         * title :  insertion with uploading has been completed
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
        /*
         * id : 12
         * name : amnayaasd
         * number : 213654
         * manufacturingCountry : 2
         * brand : 1
         * model : 2
         * category : 6
         * price : 2589
         * currency : LE
         * image1 : http://myspare.net/public/upload/contact_center.jpg
         * image2 : http://myspare.net/public/upload/contact_center.jpg
         * image3 : http://myspare.net/public/upload/contact_center.jpg
         * country : 6
         * date : 2019
         */

        private String id;
        private String name;
        private String number;
        private String manufacturingCountry;
        private String brand;
        private String model;
        private String category;
        private String price;
        private String currency;
        private String image1;
        private String image2;
        private String image3;
        private String country;
        private String date;

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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getManufacturingCountry() {
            return manufacturingCountry;
        }

        public void setManufacturingCountry(String manufacturingCountry) {
            this.manufacturingCountry = manufacturingCountry;
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
