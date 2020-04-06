package com.hackathon.camerabuilder.ui;

import com.hackathon.camerabuilder.api.model.Product;

public class Lens extends Product {

    private String compatibleMount;

    public String getCompatibleMount() {
        return compatibleMount;
    }

    public void setCompatibleMount(String compatibleMount) {
        this.compatibleMount = compatibleMount;
    }
}
