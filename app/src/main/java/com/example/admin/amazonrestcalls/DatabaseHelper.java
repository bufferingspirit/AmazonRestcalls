package com.example.admin.amazonrestcalls;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.util.ArrayList;


/**
 * Created by Admin on 8/20/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "MyDatabase" ;

    public static final String TABLE_NAME = "Profiles";
    public static final String TITLE = "Title";
    public static final String AUTHOR = "Author";
    public static final String PICTURE = "Picture";
    public static final String TAG = "DatabaseHelper";

    Context context;
    boolean check_flag;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME + " ( "
                + TITLE + " TEXT, "
                + AUTHOR + " TEXT, "
                + PICTURE + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long saveProfiles(ArrayList<Profile> profiles){
        SQLiteDatabase database = this.getWritableDatabase();

        for(int i = 0; i < profiles.size(); i++) {
            Book book = new Book(null, null, null);
            ContentValues contentValues = new ContentValues();

            book.setTitle(profiles.get(i).getTitle());
            book.setAuthor(profiles.get(i).getAuthor());
            book.setPicture(profiles.get(i).getImageURL());

            String title = profiles.get(i).getTitle().replaceAll("'", "\''");
            contentValues.put(TITLE, title);
            contentValues.put(AUTHOR, profiles.get(i).getAuthor());
            contentValues.put(PICTURE, profiles.get(i).getImageURL());

            database.insert(TABLE_NAME, null, contentValues);
            Intent intent = new Intent();
            intent.setAction("MY_ACTION.pictures_loaded");
            intent.putExtra("book", book);
            context.sendBroadcast(intent);
        }
        check_flag = true;
        database.close();
        return 0;
    }

    public ArrayList<Profile> getProfile(){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = database.rawQuery(query, null);

        ArrayList<Profile> profileList = new ArrayList<Profile>();
        if(cursor.moveToFirst()){
            do{
                Profile profile = new Profile(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                profileList.add(profile);
                //Log.d(TAG, "getTitle:" + cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return profileList;
    }

    public boolean isRowPresent(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + TITLE + " = '" + title + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            db.close();
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    public boolean checkDataBase() {
        File dbFile = context.getDatabasePath(TABLE_NAME);
        return dbFile.exists() && check_flag;
    }
}
