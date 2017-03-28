package com.example.dylan.eigentify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "AnimalFriends";
    // Contacts table name
    private static final String TABLE_ALLPEOPLE = "allPeople";
    // allPeople Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST = "First";
    private static final String KEY_LAST = "Last";
    private static final String KEY_MAJOR = "Major";
    private static final String KEY_MISC = "Misc";
    private static final String KEY_IMAGE = "image";
    //________________________

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ALLPEOPLE + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRST + " TEXT,"
        + KEY_LAST + " TEXT," + KEY_MAJOR + " TEXT," + KEY_MISC + " TEXT," + KEY_IMAGE + " BLOB" + ")";
        //________________________
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    /*

                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT" + " %s LONGITUDE" + "%s LATITUDE)",
                         TaskContract.Columns.POI,
                        TaskContract.LONGITUDES, TaskContract.Columns.LONGITUDE,
                        TaskContract.LATITUDES, TaskContract.Columns.LATITUDE);

     */









    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALLPEOPLE);
// Creating tables again
        onCreate(db);
    }
    // Adding new PersonInfo
    public void addPersonInfo(PersonInfo PersonInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST, PersonInfo.getFirst()); // PersonInfo Name
        values.put(KEY_LAST, PersonInfo.getLast()); // PersonInfo Name
        values.put(KEY_MAJOR, PersonInfo.getMajor()); // PersonInfo Phone Number
        values.put(KEY_MISC, PersonInfo.getMisc()); // PersonInfo Name
        values.put(KEY_IMAGE, PersonInfo.getImage());
        //________________________

// Inserting Row
        db.insert(TABLE_ALLPEOPLE, null, values);
        db.close(); // Closing database connection
    }
    // Getting one PersonInfo
    public PersonInfo getPersonInfo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ALLPEOPLE, new String[]{KEY_ID,
                KEY_FIRST, KEY_LAST, KEY_MAJOR, KEY_MISC, KEY_IMAGE}, KEY_ID + "=?",
        new String[]{String.valueOf(id)}, null, null, null, null);
        //________________________
        if (cursor != null)
            cursor.moveToFirst();

        PersonInfo contact = new PersonInfo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getBlob(5));
        //________________________
// return PersonInfo
        return contact;
    }
    // Getting All allPeople
    public List<PersonInfo> getAllPeople() {
        List<PersonInfo> PersonInfoList = new ArrayList<PersonInfo>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ALLPEOPLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PersonInfo PersonInfo = new PersonInfo();
                PersonInfo.setId(Integer.parseInt(cursor.getString(0)));
                PersonInfo.setFirst(cursor.getString(1));
                PersonInfo.setLast(cursor.getString(2));
                PersonInfo.setMajor(cursor.getString(3));
                PersonInfo.setMisc(cursor.getString(4));
                PersonInfo.setImage(cursor.getBlob(5));
                //________________________
// Adding contact to list
                PersonInfoList.add(PersonInfo);
            } while (cursor.moveToNext());
        }

// return contact list
        return PersonInfoList;
    }
    // Getting allPeople Count
    public int getallPeopleCount() {
        String countQuery = "SELECT * FROM " + TABLE_ALLPEOPLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

// return count
        return cursor.getCount();
    }
    // Updating a PersonInfo
    public int updatePersonInfo(PersonInfo PersonInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST, PersonInfo.getFirst());
        values.put(KEY_LAST, PersonInfo.getLast());
        values.put(KEY_MAJOR, PersonInfo.getMajor());
        values.put(KEY_MISC, PersonInfo.getMisc());
        values.put(KEY_IMAGE, PersonInfo.getImage());
        //________________________

// updating row
        return db.update(TABLE_ALLPEOPLE, values, KEY_ID + " = ?",
        new String[]{String.valueOf(PersonInfo.getId())});
    }

    // Deleting a PersonInfo
    public void deletePersonInfo(PersonInfo PersonInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALLPEOPLE, KEY_ID + " = ?",
        new String[] { String.valueOf(PersonInfo.getId()) });
        db.close();
    }









}