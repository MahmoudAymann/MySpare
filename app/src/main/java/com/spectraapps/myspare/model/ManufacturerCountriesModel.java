package com.spectraapps.myspare.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by MahmoudAyman on 13/02/2018.
 */

public class ManufacturerCountriesModel implements Parcelable{
    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : [{"id":"11","name":"تركيا"},{"id":"12","name":"اليمن"},{"id":"13","name":"استراليا"},
     * {"id":"14","name":"كندا"},{"id":"15","name":"الأرجنتين"},{"id":"16","name":"الولايات المتحده الأمريكية"},
     * {"id":"17","name":"المانيا"},{"id":"18","name":"الصين"},{"id":"19","name":"كوريا"},{"id":"20","name":"ماليزيا"},{"id":"21","name":"ايطاليا"},
     * {"id":"23","name":"فرنسا"},{"id":"24","name":"روسيا"},{"id":"25","name":"اليابان"},{"id":"29","name":"الهند"}]
     */

    private StatusBean status;
    private List<DataBean> data;

    protected ManufacturerCountriesModel(Parcel in) {
    }

    public static final Creator<ManufacturerCountriesModel> CREATOR = new Creator<ManufacturerCountriesModel>() {
        @Override
        public ManufacturerCountriesModel createFromParcel(Parcel in) {
            return new ManufacturerCountriesModel(in);
        }

        @Override
        public ManufacturerCountriesModel[] newArray(int size) {
            return new ManufacturerCountriesModel[size];
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
         * id : 11
         * name : تركيا
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
