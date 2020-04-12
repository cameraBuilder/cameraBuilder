package com.hackathon.camerabuilder.api.model;

import java.util.ArrayList;

public class ActualKit {

    private Integer id;
    private ArrayList<KitItem> kitItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<KitItem> getKitItems() {
        return kitItems;
    }

    public void setKitItems(ArrayList<KitItem> kitItems) {
        this.kitItems = kitItems;
    }
}
