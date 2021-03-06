package com.spectraapps.myspare.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by MahmoudAyman on 11/02/2018.
 */

public class CategoriesModel implements Parcelable{

    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : [{"id":"1","name":"Internals","image":"http://myspare.net/api/images/Internals.png"},{"id":"2","name":"Externals","image":"http://myspare.net/api/images/Externals.png"},{"id":"3","name":"Mechanics","image":"http://myspare.net/api/images/Mechanics.png"},{"id":"4","name":"Tires","image":"http://myspare.net/api/images/Tires.png"},{"id":"5","name":"Accessories","image":"http://myspare.net/api/images/Accessories.png"},{"id":"6","name":"Electricities","image":"http://myspare.net/api/images/Electricities.png"}]
     */

    private StatusBean status;
    private List<DataBean> data;

    protected CategoriesModel(Parcel in) {
    }

    public static final Creator<CategoriesModel> CREATOR = new Creator<CategoriesModel>() {
        @Override
        public CategoriesModel createFromParcel(Parcel in) {
            return new CategoriesModel(in);
        }

        @Override
        public CategoriesModel[] newArray(int size) {
            return new CategoriesModel[size];
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
         * name : Internals
         * image : http://myspare.net/api/images/Internals.png
         */

        private String id;
        private String name;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
