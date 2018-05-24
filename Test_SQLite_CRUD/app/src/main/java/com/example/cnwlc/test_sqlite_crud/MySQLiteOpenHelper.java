package com.example.cnwlc.test_sqlite_crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlStudent = "create table if not exists student" +
                "(_id integer primary key autoincrement," +
                "name text, age integer, address text);";
        db.execSQL(sqlStudent);

        String sqlTeacher = "create table if not exists teacher" +
                "(_id integer primary key autoincrement," +
                "name text, age integer, address text, content text);";
        db.execSQL(sqlTeacher);

        System.out.println("MySQLiteOpenHelper_onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlStudent = "drop table if exists student;";
        db.execSQL(sqlStudent);
        String sqlTeacher = "drop table if exists teacher;";
        db.execSQL(sqlTeacher);
        onCreate(db); // 다시 테이블 생성
    }
}