package com.unisight.unisight;

class DynamicListViewItem {
    private String title;
    private String content;

    DynamicListViewItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
