package com.example.dylan.eigentify;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import android.util.SparseArray;
import android.widget.Toast;

import android.util.Log;
import java.util.List;
import java.util.ArrayList;
import android.widget.ListView;
import android.graphics.BitmapFactory;


import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    // Create database
    ArrayList<PersonInfo> imgArr = new ArrayList<PersonInfo>();
    ImageAdapter adapter;
    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();
    static final int STORE_IMAGE_CAPTURE = 1;
    static final int RECOGNIZE_IMAGE_CAPTURE = 2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == STORE_IMAGE_CAPTURE && resultCode == RESULT_OK){
            // open "Enter Info" screen
            //startActivity(new Intent(MainActivity.this, EnterInfoActivity.class));
            // convert bitmap (picture) to bytearray and send to "Enter Info" screen

            // Initialize bitmap and imageview
            Bitmap tempBitmap;
            //ImageView myImageView = (ImageView) findViewById(R.id.imageView);

            // convert picture to bitmap
            Bundle extras = data.getExtras();
            Bitmap myBitmap = (Bitmap) extras.get("data");

            tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(),myBitmap.getHeight(), Bitmap.Config.RGB_565);

            //draw rectangle
            Paint myRectPaint = new Paint();
            myRectPaint.setStrokeWidth(5);
            myRectPaint.setColor(Color.RED);
            myRectPaint.setStyle(Paint.Style.STROKE);

            //more bitmap stuff
            Canvas tempCanvas = new Canvas(tempBitmap);
            tempCanvas.drawBitmap(myBitmap,0,0,null);

            // start face detector
            FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).build();
            if(!faceDetector.isOperational()) {
                Toast.makeText(this,"Couldnot set up the Face Detector", Toast.LENGTH_LONG).show();
                return;
            }

            // create frame
            Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
            SparseArray <Face> faces = faceDetector.detect(frame);

            // get coords
            for(int i=0; i<faces.size();i++) {
                Face thisFace = faces.valueAt(i);
                float x1 = thisFace.getPosition().x;
                float y1 = thisFace.getPosition().y;
                float x2 = x1 + thisFace.getWidth();
                float y2 = y1 + thisFace.getHeight();
                tempCanvas.drawRoundRect(new RectF(x1,y1,x2,y2),2,2,myRectPaint);
            }

            // set imageView to image
            //myImageView.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));

            //Converts to bit array
            int i = 0;
            Log.d("Insert: ", "Inserting ..");
            Bitmap image = tempBitmap;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte imageInByte[] = stream.toByteArray();

            //Stores information in database
            //db.addPersonInfo(new PersonInfo(i, "Dylan", "Leggio", "Computer Science", "Senior", imageInByte));

            // send picture to "Enter Info" screen
            Intent sendPicture = new Intent(MainActivity.this, EnterInfoActivity.class);
            sendPicture.putExtra("picture",imageInByte);
            startActivity(sendPicture);

        }

        else if (requestCode == RECOGNIZE_IMAGE_CAPTURE && resultCode == RESULT_OK){
            // convert picture to bitmap
            Bundle extras = data.getExtras();
            Bitmap myBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Log.d("Reading:","Reading all people...");
            List<PersonInfo> people = db.getAllPeople();
            for(PersonInfo person : people){
                String log = "Name: " + person.getFirst();
                Log.d("Person::",log);
            }


            // send to "Info card" screen
            Intent sendPicture = new Intent(MainActivity.this, InfoCardActivity.class);
            sendPicture.putExtra("picture",byteArray);

            // open "Info Card" screen
            startActivity(sendPicture);

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
