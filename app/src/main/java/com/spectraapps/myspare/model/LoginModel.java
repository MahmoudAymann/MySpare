package com.spectraapps.myspare.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by MahmoudAyman on 31/01/2018.
 */

public class LoginModel implements Serializable{
    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : {"id":"7","name":"Amr Mohamed","mail":"iamrmohamed@gmail.com","mobile":"01004676701","token":"123"}
     */
    private StatusBean status;
    private DataBean data;
    public LoginModel() {
    }
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
        private String id;
        private String name;
        private String mail;
        private String mobile;
        private String token;

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
    }
}