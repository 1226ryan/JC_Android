package com.example.administrator.test_recyclerview.main;

public class RecyclerItem {

    private String name;
    private int imageResouce;

    public RecyclerItem(String name, int imageResouce) {
        this.name = name;
        this.imageResouce = imageResouce;
    }

    public String getName() {
        return name;
    }

    public int getImageResouce() {
        return imageResouce;
    }
}
