package com.spectraapps.myspare.model;

/**
 * Created by MahmoudAyman on 13/02/2018.
 */

public class ResetPasswordModel {


    /**
     * status : {"type":"success","title":"email sent successfully"}
     */

    private StatusBean status;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public static class StatusBean {
        /**
         * type : success
         * title : email sent successfully
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
}
