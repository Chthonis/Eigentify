package com.example.dylan.eigentify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.view.View;

public class InfoCardActivity extends AppCompatActivity {

    //ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_card);

        // set imageview to previously taken image
        ImageView iv = (ImageView) findViewById(R.id.imageView2);
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        iv.setImageBitmap(bmp);

    }

    public void goToHome(View view){
        startActivity(new Intent(InfoCardActivity.this,MainActivity.class));
    }



}
