package com.example.dylan.eigentify;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import java.util.ArrayList;
import com.kairos.*;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    static final int STORE_IMAGE_CAPTURE = 1;
    static final int RECOGNIZE_IMAGE_CAPTURE = 2;

    // Create database
    ArrayList<PersonInfo> imgArr = new ArrayList<PersonInfo>();
    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        // FACE DETECTION
        if(requestCode == STORE_IMAGE_CAPTURE && resultCode == RESULT_OK){

            // convert picture to bitmap
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");

            // convert bitmap to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte imageInByte[] = stream.toByteArray();

            // send bitmap to "Enter Info" Screen
            Intent sendPicture = new Intent(MainActivity.this, EnterInfoActivity.class);
            sendPicture.putExtra("picture",imageInByte);
            startActivity(sendPicture);

        }

        // FACE RECOGNITION
        else if (requestCode == RECOGNIZE_IMAGE_CAPTURE && resultCode == RESULT_OK){

            // convert picture to bitmap
            Bundle extras = data.getExtras();
            Bitmap myBitmap = (Bitmap) extras.get("data");

            // instantiate a new kairos instance
            Kairos myKairos = new Kairos();
            String app_id = "75317862";
            String api_key = "4dde039f934d6cbd1001bee205dc8c17";
            myKairos.setAuthentication(this,app_id,api_key);
            KairosListener listener = new KairosListener(){
                @Override
                public void onSuccess(String response) {
                    // your code here!
                    Log.d("KAIROS", response);
                }

                @Override
                public void onFail(String response) {
                    // your code here!
                    Log.d("KAIROS", response);
                }
            };

            // attempt to recognize

            try{
                myKairos.recognize(myBitmap,"friends",null,null,null,null,listener);
            } catch (JSONException|UnsupportedEncodingException e) {
                Log.d("KAIROS", "JSON/ENCODING ERROR");
                e.printStackTrace();
                System.exit(-1);
            } finally{

            }

        }

    }

    // called when "Store" button is pressed
    public void storePhoto(View view){

        // open camera
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, STORE_IMAGE_CAPTURE);
        }
    }

    // called when "Recognize" button is pressed
    public void recognizePhoto(View view){

        // open camera
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, RECOGNIZE_IMAGE_CAPTURE);
        }
    }
}
