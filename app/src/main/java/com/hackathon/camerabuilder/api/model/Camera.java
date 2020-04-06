package com.hackathon.camerabuilder.api.model;

public class Camera extends Product{

private String type;
private String mountType;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getMountType() {
return mountType;
}

public void setMountType(String mountType) {
this.mountType = mountType;
}

}