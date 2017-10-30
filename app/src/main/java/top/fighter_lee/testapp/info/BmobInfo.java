package top.fighter_lee.testapp.info;

import cn.bmob.v3.BmobObject;

/**
 * @author fighter_lee
 * @date 2017/10/30
 */
public class BmobInfo extends BmobObject {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPushkey() {
        return pushkey;
    }

    public void setPushkey(String pushkey) {
        this.pushkey = pushkey;
    }

    public String getPayweb() {
        return payweb;
    }

    public void setPayweb(String payweb) {
        this.payweb = payweb;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String username;
    public String web;
    public String pushkey;
    public String payweb;
    public String isshow;

}
