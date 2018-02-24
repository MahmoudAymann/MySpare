package com.spectraapps.myspare.model;

import java.util.List;

/**
 * Created by MahmoudAyman on 24/02/2018.
 */

public class CurrencyModel {
    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : [{"id":"1","name":"euro"},{"id":"2","name":"dollar"},{"id":"3","name":"Egyptian pound"},{"id":"4","name":"sudan pound"},{"id":"5","name":"bahraini dinar"},{"id":"6","name":"omani rial"},{"id":"7","name":"qatar rial"},{"id":"8","name":"emirati dirham"},{"id":"9","name":"saudi riyal"}]
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
         * id : 1
         * name : euro
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
