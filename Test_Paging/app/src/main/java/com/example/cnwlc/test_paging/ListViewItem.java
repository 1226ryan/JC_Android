package com.example.cnwlc.test_paging;

/**
 * Created by cnwlc on 2018-02-18.
 */

public class ListViewItem {

    private String Stitle;
    private String Sdaily;
    private String Simport;

    public ListViewItem(String Stitle, String Sdaily,  String Simport) {
        this.Stitle   = Stitle;
        this.Sdaily   = Sdaily;
        this.Simport  = Simport;
    }

    public String getStitle(){
        return Stitle;
    }
    public String getSdaily() {
        return Sdaily;
    }
    public String getSimport() {
        return Simport;
    }
}