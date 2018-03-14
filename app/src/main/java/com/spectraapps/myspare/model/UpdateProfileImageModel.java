package com.spectraapps.myspare.model;

/**
 * Created by MahmoudAyman on 04/03/2018.
 */

public class UpdateProfileImageModel {

    /**
     * status : {"type":"success","title":" insertion with uploading has been completed "}
     * data : {"id":"1","name":"Mahmoud Ayman","mail":"madopop007@yahoo.com",
     * "mobile":"1118031092","token":"123","image":"http://myspare.net/public/upload/15209361211434223981465.jpg"}
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
         * id : 1
         * name : Mahmoud Ayman
         * mail : madopop007@yahoo.com
         * mobile : 1118031092
         * token : 123
         * image : http://myspare.net/public/upload/15209361211434223981465.jpg
         */

        private String id;
        private String name;
        private String mail;
        private String mobile;
        private String token;
        private String image;

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

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
