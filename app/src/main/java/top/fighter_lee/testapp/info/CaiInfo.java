package top.fighter_lee.testapp.info;


public class CaiInfo {


    /**
     * data : {"0":"1","id":"1","1":"http://m.6769c.com","url":"http://m.6769c.com","2":"android","type":"android","3":"1","show_url":"1","4":"test","appid":"test","5":"test","comment":"test","6":"2009-10-20 11:10:45","createAt":"2009-10-20 11:10:45","7":"2009-12-21 07:12:47","updateAt":"2009-12-21 07:12:47"}
     * rt_code : 200
     */

    private DataBean data;
    private String rt_code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getRt_code() {
        return rt_code;
    }

    public void setRt_code(String rt_code) {
        this.rt_code = rt_code;
    }

    public static class DataBean {
        /**
         * 0 : 1
         * id : 1
         * 1 : http://m.6769c.com
         * url : http://m.6769c.com
         * 2 : android
         * type : android
         * 3 : 1
         * show_url : 1
         * 4 : test
         * appid : test
         * 5 : test
         * comment : test
         * 6 : 2009-10-20 11:10:45
         * createAt : 2009-10-20 11:10:45
         * 7 : 2009-12-21 07:12:47
         * updateAt : 2009-12-21 07:12:47
         */

        private String id;
        private String url;
        private String type;
        private String show_url;
        private String appid;
        private String comment;
        private String createAt;
        private String updateAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getShow_url() {
            return show_url;
        }

        public void setShow_url(String show_url) {
            this.show_url = show_url;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public String getUpdateAt() {
            return updateAt;
        }

        public void setUpdateAt(String updateAt) {
            this.updateAt = updateAt;
        }
    }
}
