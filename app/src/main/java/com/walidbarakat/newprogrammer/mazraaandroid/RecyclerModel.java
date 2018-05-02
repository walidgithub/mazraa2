package com.walidbarakat.newprogrammer.mazraaandroid;

/**
 * Created by PC on 15/04/2018.
 */

public class RecyclerModel {
    public String name;
    public byte[] img;

    public RecyclerModel(String name, byte[] img) {
        this.name = name;
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public byte[] getImg() {
        return img;
    }
}
