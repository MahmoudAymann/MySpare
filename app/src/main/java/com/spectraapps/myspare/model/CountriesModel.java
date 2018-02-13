package com.spectraapps.myspare.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by MahmoudAyman on 13/02/2018.
 */

public class CountriesModel implements Parcelable{

    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : [{"id":"1","name":"Egypt"},{"id":"2","name":"Syria"},{"id":"3","name":"Qatar"},{"id":"4","name":"Emirates"},{"id":"5","name":"kuwait"},{"id":"6","name":"oman"},{"id":"7","name":"iraq"},{"id":"8","name":"Algeria"},{"id":"9","name":"iran"},{"id":"10","name":"tunisia"},{"id":"11","name":"turkey"},{"id":"12","name":"yemen"},{"id":"13","name":"australia"},{"id":"14","name":"canada"},{"id":"15","name":"argentina"},{"id":"16","name":"USA"},{"id":"17","name":"germany"},{"id":"18","name":"china"},{"id":"19","name":"korea"},{"id":"20","name":"malaysia"},{"id":"21","name":"italy"},{"id":"23","name":"france"},{"id":"24","name":"russia"},{"id":"25","name":"japan"},{"id":"29","name":"india"},{"id":"30","name":"mexico"},{"id":"31","name":"brazil"}]
     */

    private StatusBean status;
    private List<DataBean> data;

    protected CountriesModel(Parcel in) {
    }

    public static final Creator<CountriesModel> CREATOR = new Creator<CountriesModel>() {
        @Override
        public CountriesModel createFromParcel(Parcel in) {
            return new CountriesModel(in);
        }

        @Override
        public CountriesModel[] newArray(int size) {
            return new CountriesModel[size];
        }
    };

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
         * id : 1
         * name : Egypt
         */

        private String id;
        private String name;

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
    }
}
