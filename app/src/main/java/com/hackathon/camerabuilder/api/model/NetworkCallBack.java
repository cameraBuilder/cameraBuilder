package com.hackathon.camerabuilder.api.model;

public interface NetworkCallBack<T> {
    void onError(String message);
    void onSuccess(T data, String message);
}
