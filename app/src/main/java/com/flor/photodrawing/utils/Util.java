package com.flor.photodrawing.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class Util {

    /**
     * Converts a bitmap to string.
     *
     * @param bitmap Bitmap to be converted.
     * @return String representation of the given Bitmap.
     */
    public static String toString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * Converts a string to bitmap.
     *
     * @param encodedString String to be converted.
     * @return Bitmap representation of the given string.
     */
    public static Bitmap toBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);

            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap viewToBitmap(View view) {

        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();

        view.draw(canvas);

        returnedBitmap.setWidth(view.getWidth());
        returnedBitmap.setHeight(view.getHeight());

        Log.e("viewToBitmap getHeight", String.valueOf(returnedBitmap.getHeight()));
        Log.e("viewToBitmap getWidth", String.valueOf(returnedBitmap.getWidth()));

        return returnedBitmap;
    }
}
