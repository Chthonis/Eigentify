package com.example.dylan.eigentify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class recognizeFail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize_fail);
    }

    public void goHome(View view){
        startActivity(new Intent(recognizeFail.this,MainActivity.class));
    }
}
