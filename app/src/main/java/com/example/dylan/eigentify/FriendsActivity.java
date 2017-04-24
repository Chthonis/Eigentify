package com.example.dylan.eigentify;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.List;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ListView;

public class FriendsActivity extends AppCompatActivity{

    ArrayList<PersonInfo> imgArr = new ArrayList<PersonInfo>();
    ImageAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        DBHandler db = new DBHandler(this);

        // Reading all allPeople
        Log.d("Reading: ", "Reading all allPeople..");
        List<PersonInfo> allPeople = db.getAllPeople();

        ArrayList<String> list = new ArrayList<>();
        for (PersonInfo pin : allPeople) {
            String log = "Id: " + pin.getId() + ", First: " + pin.getFirst() + ", Last: " + pin.getLast() + ", Major: " + pin.getMajor() + ", Misc: " + pin.getMisc() + ", Image: " + pin.getImage();
            // Writing allPeople to log
            Log.d("PersonInfo: : ", log);
            // check for bad entries
            if(pin.getFirst().contains("android")) db.deletePersonInfo(pin);
            // check if we've printed user to list or not to avoid duplicates
            if(list.contains(pin.getLast())) continue;
            else{
                imgArr.add(pin);
                list.add(pin.getLast());
            }
        }

        adapter = new ImageAdapter(this, R.layout.screen_list, imgArr);
        ListView dataList = (ListView)findViewById(R.id.list);
        dataList.setAdapter(adapter);

    }
}