package com.spectraapps.myspare.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spectraapps.myspare.model.Data;
import com.spectraapps.myspare.model.Status;

import java.io.Serializable;

/**
 * Created by MahmoudAyman on 14/02/2018.
 */

public class login implements Serializable {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("data")
    @Expose
    private Data data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
