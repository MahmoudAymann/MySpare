package com.spectraapps.myspare.model;

import java.util.List;

/**
 * Created by mahmo on 3/10/2018.
 */

public class NotificationModel {

    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : [{"id":"4","user_id":"26","image":"http://myspare.net/public/upload/2.jpg","text":"hellooooooooooooooo","created_at":"2018-02-18 21:11:25","updated_at":"2018-02-18 21:11:25"}]
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
         * id : 4
         * user_id : 26
         * image : http://myspare.net/public/upload/2.jpg
         * text : hellooooooooooooooo
         * created_at : 2018-02-18 21:11:25
         * updated_at : 2018-02-18 21:11:25
         */

        private String id;
        private String user_id;
        private String image;
        private String text;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
