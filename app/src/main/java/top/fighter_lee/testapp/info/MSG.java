package top.fighter_lee.testapp.info;

import cn.bmob.v3.BmobObject;

public class MSG extends BmobObject{

    public String web;
    public String pay;
    public Boolean isshow;

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public Boolean getIsshow() {
        return isshow;
    }

    public void setIsshow(Boolean isshow) {
        this.isshow = isshow;
    }


}
