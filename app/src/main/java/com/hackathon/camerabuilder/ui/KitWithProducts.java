package com.hackathon.camerabuilder.ui;

import com.hackathon.camerabuilder.api.model.Product;

import java.util.ArrayList;

public class KitWithProducts {

    private ArrayList<Product> products;
    private  int id;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
