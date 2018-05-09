package com.example.cnwlc.app_memo.Memo;

public class Item {
    private String sTitle;
    private String sDaily;
    private String sImport;

    public Item(String sTitle, String sDaily, String sImport) {
        this.sTitle = sTitle;
        this.sDaily = sDaily;
        this.sImport = sImport;
    }

    public String getsTitle() {
        return sTitle;
    }

    public String getsDaily() {
        return sDaily;
    }

    public String getsImport() {
        return sImport;
    }
}
