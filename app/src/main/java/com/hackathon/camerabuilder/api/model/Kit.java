package com.hackathon.camerabuilder.api.model;

import java.util.ArrayList;

public class Kit {

    private String items;
    private boolean isOver;
    private Integer userId;



    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
