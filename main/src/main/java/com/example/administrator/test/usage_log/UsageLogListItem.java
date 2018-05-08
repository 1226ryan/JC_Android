package com.example.administrator.test.usage_log;

/**
 * Created by Administrator on 2018-02-14.
 */

public class UsageLogListItem {

    private String Stitle;
    private String StimeIp;

    public UsageLogListItem(String Stitle, String StimeIp) {
        this.Stitle   = Stitle;
        this.StimeIp = StimeIp;
    }

    public String getStitle(){
        return Stitle;
    }
    public String getStimeIp() {
        return StimeIp;
    }
}