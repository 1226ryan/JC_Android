package com.example.administrator.test_app.etc;

public class RecyclerItem {

    private String Stitle;
    private String StimeIp;

    public RecyclerItem(String Stitle, String StimeIp) {
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
