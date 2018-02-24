package com.spectraapps.myspare.model;

import java.util.List;

/**
 * Created by MahmoudAyman on 24/02/2018.
 */

public class BrandsModel {
    /**
     * status : {"type":"success","title":"Successfull request"}
     * data : [{"id":"1","name":"acura"},{"id":"2","name":"alf romeo"},{"id":"3","name":"aston martin"},{"id":"4","name":"audi"},{"id":"5","name":"bentley"},{"id":"6","name":"bmw"},{"id":"7","name":"buggati"},{"id":"8","name":"buick"},{"id":"9","name":"cadillac"},{"id":"10","name":"chevrolet"},{"id":"11","name":"chrysler"},{"id":"12","name":"citroen"},{"id":"13","name":"dodge"},{"id":"14","name":"ferari"},{"id":"15","name":"fiat"},{"id":"16","name":"ford"},{"id":"17","name":"jeely"},{"id":"18","name":"general motors"},{"id":"19","name":"gmc"},{"id":"20","name":"honda"},{"id":"21","name":"hyundai"},{"id":"22","name":"infiniti"},{"id":"23","name":"jaguar"},{"id":"24","name":"jeep"},{"id":"25","name":"kia"},{"id":"26","name":"lamborghini"},{"id":"27","name":"land rover"},{"id":"28","name":"mazda"},{"id":"29","name":"mini"},{"id":"30","name":"mitsubishi"},{"id":"31","name":"nissan"},{"id":"32","name":"peugeot"},{"id":"33","name":"porche"},{"id":"34","name":"renault"},{"id":"35","name":"subaru"},{"id":"36","name":"suzuki"},{"id":"37","name":"toyota"},{"id":"38","name":"seat"},{"id":"39","name":"volkswagen"},{"id":"40","name":"volvo"},{"id":"46","name":"mercedes-benz"}]
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
         * name : acura
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
