package com.example.administrator.test_recyclerview.another2;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private String emailId;
    private boolean isSelected;

    public Student(String name, String emailId, boolean isSelected) {
        this.name = name;
        this.emailId = emailId;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}