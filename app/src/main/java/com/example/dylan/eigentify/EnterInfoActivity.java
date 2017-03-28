package com.example.dylan.eigentify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

public class EnterInfoActivity extends AppCompatActivity {

    byte[] byteArray;
    DBHandler db;
    TextView first;
    TextView last;
    TextView major;
    TextView year;

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


    }

    public void storeInfo(View view){
        //startActivity(new Intent(EnterInfoActivity.this,storeSuccess.class));
        int i = 0;
        db.addPersonInfo(new PersonInfo(i, first.getText().toString(), last.getText().toString(), year.getText().toString(), major.getText().toString(), byteArray));
        Intent sendPicture = new Intent(EnterInfoActivity.this,storeSuccess.class);
        sendPicture.putExtra("picture",byteArray);
        startActivity(sendPicture);
    }
}
