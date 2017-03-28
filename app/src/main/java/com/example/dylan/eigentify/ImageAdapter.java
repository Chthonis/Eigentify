package com.example.dylan.eigentify;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;


public class ImageAdapter extends ArrayAdapter<PersonInfo>{

    Context context;
    int layoutResourceId;
    ArrayList<PersonInfo> data=new ArrayList<PersonInfo>();
    public ImageAdapter(Context context, int layoutResourceId, ArrayList<PersonInfo> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ImageHolder();
            holder.firstTitle = (TextView)row.findViewById(R.id.firstTitle);
            holder.lastTitle = (TextView)row.findViewById(R.id.lastTitle);
            holder.majorTitle = (TextView)row.findViewById(R.id.majorTitle);
            holder.miscTitle = (TextView)row.findViewById(R.id.miscTitle);
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }
        PersonInfo picture = data.get(position);
        holder.firstTitle.setText(picture.getFirst());
        holder.lastTitle.setText(picture.getLast());
        holder.majorTitle.setText(picture.getMajor());
        holder.miscTitle.setText(picture.getMisc());

//convert byte to bitmap take from PersonInfo class
        byte[] outImage=picture.getImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);
        return row;
    }
    static class ImageHolder
    {
        ImageView imgIcon;
        TextView firstTitle;
        TextView lastTitle;
        TextView majorTitle;
        TextView miscTitle;
    }





}


/*

    public void insertImg(int id, Bitmap img){
        byte[] data = bitmapToByteArr(img);
        insertStatement_logo.bindLong(1, id);



    }

    public static byte[] bitmapToByteArr(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

 */