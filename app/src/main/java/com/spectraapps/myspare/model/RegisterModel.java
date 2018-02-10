package com.spectraapps.myspare.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MahmoudAyman on 10/02/2018.
 */

public class RegisterModel implements Parcelable{

    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : {"name":"aaaa","mail":"ahmedal778@yahoo.com","mobile":"012225555","password":"123456789","token":"tfyfytfytfytfytfyfytfytfy"}
     */

    private StatusBean status;
    private DataBean data;

    protected RegisterModel(Parcel in) {
    }

    public static final Creator<RegisterModel> CREATOR = new Creator<RegisterModel>() {
        @Override
        public RegisterModel createFromParcel(Parcel in) {
            return new RegisterModel(in);
        }

        @Override
        public RegisterModel[] newArray(int size) {
            return new RegisterModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
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
         * name : aaaa
         * mail : ahmedal778@yahoo.com
         * mobile : 012225555
         * password : 123456789
         * token : tfyfytfytfytfytfyfytfytfy
         */

        private String name;
        private String mail;
        private String mobile;
        private String password;
        private String token;

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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
