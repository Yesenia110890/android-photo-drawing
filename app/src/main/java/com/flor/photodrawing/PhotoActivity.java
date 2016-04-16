
package com.flor.photodrawing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class PhotoActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

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

    public void initProperties() {

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        fabSave.setEnabled(false);


    }

    @OnClick(R.id.save)
    public void savePhoto(View v) {
        Snackbar.make(v, "Save", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnClick(R.id.photo)
    public void drawingPhoto(View v) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            Bitmap mBitmap = (Bitmap) data.getExtras().get("data");

            imvPhoto.setImageBitmap(mBitmap);
            imvPhoto.setMaxZoom(4f);

        } catch (NullPointerException e) {

        }
    }
}