package com.example.tsar.booksherlockholmes.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tsar on 27.02.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SherlockHolmesDB.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WORDS_TABLE = "CREATE TABLE " + DictionaryProvider.TABLE_NAME + "("
                + DictionaryProvider._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DictionaryProvider.COLUMN_WORD + " TEXT, "
                + DictionaryProvider.COLUMN_TRANSLATE + " TEXT"
                + ");";

        db.execSQL(SQL_CREATE_WORDS_TABLE);
        Log.e("MyLog", "DB CREATED");
        ContentValues cv = new ContentValues();
        for(int i = 1; i <3; i++ ){
            cv.put(DictionaryProvider.COLUMN_WORD, "word " + i);
            cv.put(DictionaryProvider.COLUMN_TRANSLATE, "tsarslate " + i);
            db.insert(DictionaryProvider.TABLE_NAME, null, cv);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + DictionaryProvider.TABLE_NAME);
//        onCreate(db);
    }
//
//    public boolean insertWord(String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_WORD, name);
//        db.insert(TABLE_NAME, null, contentValues);
//        return true;
//    }
//
//    public ArrayList<String> getAllWords(){
//        ArrayList<String> arrayList= new ArrayList<String>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//        res.moveToFirst();
//
//        while (!res.isAfterLast()){
//            arrayList.add(res.getString(res.getColumnIndex(COLUMN_WORD)));
//            res.moveToNext();
//        }
//        res.close();
//        return arrayList;
//    }
}
