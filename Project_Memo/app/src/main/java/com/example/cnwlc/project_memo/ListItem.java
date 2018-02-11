package com.example.cnwlc.project_memo;

import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {

    private String Stitle;
    private String Scontent;
    private String Sdaily;
    private String Simport;
    private String Suri;

    public ListItem() {}

    //Parcelable를 생성하기 위한 생성자 //임의 생성
    public ListItem(String Stitle, String Scontent, String Sdaily,  String Simport, String Suri) {
        this.Stitle   = Stitle;
        this.Scontent = Scontent;
        this.Sdaily   = Sdaily;
        this.Simport  = Simport;
        this.Suri     = Suri;
    }

    //Parcelable를 생성하기 위한 생성자 Parcel를 파라메타로 넘겨 받음
    protected ListItem(Parcel in) {
        Stitle   = in.readString();
        Scontent = in.readString();
        Sdaily   = in.readString();
        Simport  = in.readString();
        Suri  = in.readString();
    }

    //생성자와 순서가 같아야함 -> 틀릴 경우 다르게 데이터가 전달된다.
    //Parcelable의 write를 구현하기 위한 Method
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Stitle);
        dest.writeString(Scontent);
        dest.writeString(Sdaily);
        dest.writeString(Simport);
        dest.writeString(Suri);
    }

    //Parcelable을 상속 받으면 필수 Method
    @Override
    public int describeContents() {
        return 0;
    }

    //Parcelable 객체로 구현하기 위한 Parcelable Method ArrayList구현 등..
    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }
        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

    public String getStitle(){
        return Stitle;
    }
    public String getScontent() {
        return Scontent;
    }
    public String getSdaily() {
        return Sdaily;
    }
    public String getSimport() {
        return Simport;
    }
    public String getSuri() {
        return Suri;
    }
}