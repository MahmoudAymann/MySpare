package com.spectraapps.myspare.model;

/**
 * Created by MahmoudAyman on 05/03/2018.
 */

public class UpdateProfileModel {

    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : {"id":"26","name":"7oda ayman","mail":"madopop007@yahoo.com","mobile":"0123456789"}
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
         * id : 26
         * name : 7oda ayman
         * mail : madopop007@yahoo.com
         * mobile : 0123456789
         */

        private String id;
        private String name;
        private String mail;
        private String mobile;

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
    }
}
