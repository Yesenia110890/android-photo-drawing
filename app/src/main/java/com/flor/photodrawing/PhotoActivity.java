
package com.flor.photodrawing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.flor.photodrawing.model.Photo;
import com.flor.photodrawing.utils.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Provides the functionality to take a picture from the camera and then draws a rectangle
 * over the picture.
 *
 * @author  Yesenia Isabel Latorre Flor
 *          Android Developer
 *          yesenia.120990@gmail.com
 */
public class PhotoActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Realm realm;

    private static Bitmap bmOriginal;

    int pattern = 0;

    @Bind(R.id.save)
    FloatingActionButton fabSave;

    @Bind(R.id.imv_photo)
    TouchImageView imvPhoto;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        initProperties();
    }

    /**
     * Initializes the properties of the activity.
     */
    public void initProperties() {
        ButterKnife.bind(this);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfig);

        RealmResults<Photo> results = realm.where(Photo.class).findAll();

        if (results.size() != 0) {
            Bitmap savedBitmapPhoto = Util.toBitmap(results.get(0).getImage());
            imvPhoto.setImageBitmap(savedBitmapPhoto);
        }

        bmOriginal = ((BitmapDrawable) imvPhoto.getDrawable()).getBitmap();

        setSupportActionBar(toolbar);
        fabSave.setVisibility(View.GONE);

    }

    @OnClick(R.id.save)
    public void savePhoto(View v) {
        saveCurrentImage();

        Snackbar.make(v, "Photo saved to this device", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.photo)
    public void drawingPhoto(View v) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);

    }

    @OnClick(R.id.touch_left_top)
    public void leftTop() {
        pattern ++;
        isValidPattern(pattern);
    }

    @OnClick(R.id.touch_right_top)
    public void rightTop() {
        pattern ++;
        isValidPattern(pattern);
    }

    @OnClick(R.id.touch_left_bottom)
    public void leftBottom() {
        pattern ++;
        isValidPattern(pattern);
    }

    @OnClick(R.id.touch_right_bottom)
    public void rightBottom() {
        pattern ++;
        isValidPattern(pattern);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pattern = 0;
        fabSave.setVisibility(View.GONE);


        try {
            Bitmap mBitmap = (Bitmap) data.getExtras().get("data");

            imvPhoto.setImageBitmap(mBitmap);
            imvPhoto.setMaxZoom(4f);

        } catch (NullPointerException e) {

        }
    }

    /**
     * Validates the integer sent as argument.
     * The function to draw a rectangle follows a pattern, if the <code>patternValue</code>
     * is valid, the rectangle is drawn or erased.
     *
     * @param patternValue The pattern value to validate.
     */
    private void isValidPattern(int patternValue) {
        if (pattern == 2){
            drawRectangleOverImage();
            fabSave.setVisibility(View.VISIBLE);
        } else if(pattern == 3){
            removeRectangle();
            fabSave.setVisibility(View.GONE);
            pattern = 0;
        }
    }

    /**
     * Draws a rectangle over the ImageView, which contains the taken picture.
     */
    private void drawRectangleOverImage() {
        bmOriginal = ((BitmapDrawable) imvPhoto.getDrawable()).getBitmap();

        float initX = 1;
        float initY = 1;
        float finalX = bmOriginal.getWidth() - 1;
        float finalY = bmOriginal.getHeight() - 1;

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        Bitmap tempBitmap = Bitmap.createBitmap(bmOriginal.getWidth(), bmOriginal.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(tempBitmap);

        //Draw the image bitmap into the cavas
        tempCanvas.drawBitmap(bmOriginal, 0, 0, null);

        //Draw everything else you want into the canvas, in this example a rectangle with rounded edges
        tempCanvas.drawRect(initX, initY, finalX, finalY, paint);

        //Attach the canvas to the ImageView
        imvPhoto.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));

    }

    private void removeRectangle(){
        imvPhoto.setImageDrawable(new BitmapDrawable(getResources(), bmOriginal));
    }

    /**
     * Saves the current image in the ImageView into Realm.
     */
    private void saveCurrentImage() {
        Bitmap currentBitmap = Util.viewToBitmap(imvPhoto);
        String currentBitmapString = Util.toString(currentBitmap);

        Photo currentPhoto = new Photo();
        currentPhoto.setImage(currentBitmapString);

        RealmResults<Photo> results = realm.where(Photo.class).findAll();
        realm.beginTransaction();
        results.clear();
        realm.commitTransaction();

        realm.beginTransaction();
        realm.copyToRealm(currentPhoto);
        realm.commitTransaction();
    }
}