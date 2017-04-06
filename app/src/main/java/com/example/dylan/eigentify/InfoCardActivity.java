package com.example.dylan.eigentify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class InfoCardActivity extends AppCompatActivity {

    DBHandler db;
    String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_card);

        TextView name = (TextView) findViewById(R.id.name);
        TextView major = (TextView) findViewById(R.id.major);
        TextView year = (TextView) findViewById(R.id.year);
        ImageView image = (ImageView) findViewById(R.id.imageView2);

        db = new DBHandler(this);

        // grab last name from intent
        Bundle extras = getIntent().getExtras();
        lastName = extras.getString("name");
        List<PersonInfo> people = db.getAllPeople();
        for(PersonInfo person : people){
            if(person.getLast().equals(lastName)){

                // found person in DB
                name.setText(person.getFirst() + " " + person.getLast());
                major.setText(person.getMajor());
                year.setText(person.getMisc());

                //set picture to stored picture
                byte[] byteArray = person.getImage();
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
                image.setImageBitmap(bmp);
                break;
            }
        }


    }

    public void goToHome(View view){
        startActivity(new Intent(InfoCardActivity.this,MainActivity.class));
    }



}
