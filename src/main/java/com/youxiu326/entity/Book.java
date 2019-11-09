package com.youxiu326.entity;

import java.io.Serializable;

/**
 * Created by lihui on 2019/1/22.
 */
public class Book implements Serializable{

    private Long id;
    private String name;
    private float price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
