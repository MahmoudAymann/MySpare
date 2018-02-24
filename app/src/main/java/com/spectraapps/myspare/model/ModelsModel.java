package com.spectraapps.myspare.model;

import java.util.List;

/**
 * Created by MahmoudAyman on 24/02/2018.
 */

public class ModelsModel {
    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : [{"id":"13","name":"8-C Compet"},{"id":"14","name":"prera"},{"id":"15","name":"159"},{"id":"16","name":"205 - serious coupes"},{"id":"17","name":"166"},{"id":"18","name":"156"},{"id":"19","name":"GTV-6"},{"id":"20","name":"mito"},{"id":"21","name":"2600"},{"id":"22","name":"145"},{"id":"23","name":"75"},{"id":"24","name":"147"},{"id":"25","name":"155"},{"id":"26","name":"Dico volunte"},{"id":"27","name":"164"},{"id":"28","name":"Arna"},{"id":"29","name":"GT"},{"id":"30","name":"Tipo-33"},{"id":"31","name":"6C"},{"id":"32","name":"GTV and spider"},{"id":"33","name":"90"},{"id":"34","name":"alfasod"},{"id":"35","name":"alpha6"},{"id":"36","name":"alpheta"},{"id":"37","name":"sprint"},{"id":"38","name":"SZ"},{"id":"39","name":"1750"},{"id":"40","name":"G-1"},{"id":"41","name":"1900"},{"id":"42","name":"Giulietta"},{"id":"43","name":"33"}]
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
         * id : 13
         * name : 8-C Compet
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
