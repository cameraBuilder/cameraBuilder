package com.hackathon.camerabuilder.ui;

import com.hackathon.camerabuilder.api.model.Product;

public class Adapter extends Product {

    private String compatibleCameraMount;
    private String compatibleLenseMount;

    public String getCompatibleCameraMount() {
        return compatibleCameraMount;
    }

    public void setCompatibleCameraMount(String compatibleCameraMount) {
        this.compatibleCameraMount = compatibleCameraMount;
    }

    public String getCompatibleLenseMount() {
        return compatibleLenseMount;
    }

    public void setCompatibleLenseMount(String compatibleLenseMount) {
        this.compatibleLenseMount = compatibleLenseMount;
    }
}
