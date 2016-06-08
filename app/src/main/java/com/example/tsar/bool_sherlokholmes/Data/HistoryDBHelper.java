package com.example.tsar.bool_sherlokholmes.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by tsar on 27.02.2016.
 */
public class HistoryDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "history.db";

    static final String TABLE_NAME = "words";
    static final String COLUMN_WORD = "word";
    static final String COLUMN_CHAPTER = "chapter";
    static final String _ID = "id";

    public HistoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_WORD + " TEXT UNIQUE NOT NULL, " +
                COLUMN_CHAPTER + " INTEGER NOT NULL " +
                ");";
        db.execSQL(SQL_CREATE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertWord(String name, Integer chapter){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORD, name);
        contentValues.put(COLUMN_CHAPTER, chapter);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<String> getAllWords(){
        ArrayList<String> arrayList= new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        while (res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(COLUMN_WORD)));
            res.moveToNext();
        }
        return arrayList;
    }
}
