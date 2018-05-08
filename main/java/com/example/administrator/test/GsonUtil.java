package com.example.administrator.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2018-02-26.
 */

public class GsonUtil<T> {
    private static GsonUtil instance;
    private Gson gson;

    private GsonUtil(){
        gson = new Gson();
    }

    public static GsonUtil getInstance() {
        if (instance == null)
            instance = new GsonUtil();

        return instance;
    }

    public T fromJson(String row, Type type) {
        return gson.fromJson(row, type);
    }

    public T fromJsonArray(JsonArray rows, Type type) {
        return gson.fromJson(rows, type);
    }
}
