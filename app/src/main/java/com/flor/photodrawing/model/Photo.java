package com.flor.photodrawing.model;

import io.realm.RealmObject;

/**
 * Enables the application to save the taken photo by the camera with the rectangle over it.
 *
 * @author  Yesenia Isabel Latorre Flor
 *          Android Developer
 *          yesenia.120990@gmail.com
 */
public class Photo extends RealmObject {
    /**
     * The image represented as string.
     */
    private String image;

    /**
     * Gets the image.
     *
     * @return The image represented as string.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image.
     * @param image The image represented as string.
     */
    public void setImage(String image) {
        this.image = image;
    }

}
