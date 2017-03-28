package com.example.dylan.eigentify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class storeSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_success);

        // grab recognized face image and display
        ImageView iv = (ImageView) findViewById(R.id.imageView3);
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        iv.setImageBitmap(bmp);
    }

    public void goToHome(View view){
        startActivity(new Intent(storeSuccess.this,MainActivity.class));
    }
}
