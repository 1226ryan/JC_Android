package com.example.administrator.test_kotlin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        SampleVal sampleVal = new SampleVal("Name", "a@a.com");
        System.out.println("sampleVal.getName() : "+sampleVal.getName());
        System.out.println("sampleVal.getEmail() : "+sampleVal.getEmail());
        System.out.println("-----------------------------------------------");

        SampleVar sampleVar = new SampleVar("Name", "a@a.com");
        System.out.println("sampleVar.getName() : "+sampleVar.getName());
        System.out.println("sampleVar.getEmail() : "+sampleVar.getEmail());
        sampleVar.setName("Change");
        sampleVar.setEmail("b@b.com");
        System.out.println("sampleVar.getName() : "+sampleVar.getName());
        System.out.println("sampleVar.getEmail() : "+sampleVar.getEmail());
        System.out.println("-----------------------------------------------");

        Sample sample = new Sample();
        System.out.println("sample.getName() : "+sample.getName());
        System.out.println("sample.getEmail() : "+sample.getEmail());
        sample.setName("Change");
        sample.setEmail("b@b.com");
        System.out.println("sample.getName() : "+sample.getName());
        System.out.println("sample.getEmail() : "+sample.getEmail());
        System.out.println("-----------------------------------------------");


    }
}
