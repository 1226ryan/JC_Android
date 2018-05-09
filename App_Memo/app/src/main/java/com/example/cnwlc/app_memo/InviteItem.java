package com.example.cnwlc.app_memo;

public class InviteItem {
    private String name;
    private String phone;
    private boolean isChecked;

    public InviteItem(String name, String phone, boolean isChecked) {
        this.name = name;
        this.phone = phone;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
