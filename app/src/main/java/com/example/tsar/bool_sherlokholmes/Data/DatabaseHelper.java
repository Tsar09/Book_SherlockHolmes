package com.example.tsar.bool_sherlokholmes.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tsar on 22.12.2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "SherlockHolmesDB.db";
    private static final String TEXT = "text";
    private static final String ID = "id";
    private static final String TABLE_NAME = "ScandalInBohemia";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, 1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getText(String id){
        Log.d("DB", "START TO GET FRAGMENT");
        SQLiteDatabase db = this.getReadableDatabase();
        String out = null;
        Log.d("DB", "TRY TO GET FRAGMENT");
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID,TEXT}, ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            Log.d("DB", "TRY TO GET STRING");
            out = cursor.getString(1);
            //return cursor.getString(1);
        }
        cursor.close();
        return out;
    }
}
