package com.example.dylan.eigentify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import com.kairos.*;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class EnterInfoActivity extends AppCompatActivity {

    byte[] byteArray;
    DBHandler db;
    TextView first;
    TextView last;
    TextView major;
    TextView year;

    // instantiate a new kairos instance
    Kairos myKairos;

    // set authentication
    String app_id = "75317862";
    String api_key = "4dde039f934d6cbd1001bee205dc8c17";

    // create an instance of the Kairos listener
    KairosListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_info);

        Bundle extras = getIntent().getExtras();
        byteArray = extras.getByteArray("picture");
        db = new DBHandler(this);

        first = (TextView) findViewById(R.id.editText3);
        last = (TextView) findViewById(R.id.editText4);
        year = (TextView) findViewById(R.id.editText5);
        major = (TextView) findViewById(R.id.editText6);

        myKairos = new Kairos();
        myKairos.setAuthentication(this,app_id,api_key);

        listener = new KairosListener(){
            @Override
            public void onSuccess(String response) {
                // your code here!
                Log.d("KAIROS DEMO", response);
            }

            @Override
            public void onFail(String response) {
                // your code here!
                Log.d("KAIROS DEMO", response);
            }
        };


    }

    public void storeInfo(View view){

        // add person to database
        db.addPersonInfo(new PersonInfo(0, first.getText().toString(), last.getText().toString(), year.getText().toString(), major.getText().toString(), byteArray));

        // convert byte array to bitmap for processing
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

        // enroll face in api gallery
        try{
            myKairos.enroll(bmp,last.getText().toString(),"friends",null,null,null,listener);
        } catch(JSONException|UnsupportedEncodingException e) {
            Log.d("KAIROS", "JSON/ENCODING ERROR");
            e.printStackTrace();
            System.exit(-1);
        } finally {

        }

        // send picture to "Store Success" page
        Intent sendPicture = new Intent(EnterInfoActivity.this,storeSuccess.class);
        sendPicture.putExtra("picture",byteArray);
        startActivity(sendPicture);
    }
}
