package com.flor.photodrawing.model;

import io.realm.RealmObject;

public class Photo extends RealmObject {
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
