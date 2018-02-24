package com.spectraapps.myspare.model;

/**
 * Created by MahmoudAyman on 24/02/2018.
 */

public class AddModel {

    /**
     * status : {"type":"success","title":" insertion with uploading has been completed "}
     * data : {"id":"3","name":"external","number":"213654","manufacturingCountry":"2","brand":"1","model":"2","category":"3","price":"2589","currency":"LE","image":"http://myspare.net/api/uploadedimage/qwe.jpg","country":"6","date":"2019"}
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
        /**
         * id : 3
         * name : external
         * number : 213654
         * manufacturingCountry : 2
         * brand : 1
         * model : 2
         * category : 3
         * price : 2589
         * currency : LE
         * image : http://myspare.net/api/uploadedimage/qwe.jpg
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
        private String image;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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
