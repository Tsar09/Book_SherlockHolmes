package com.example.tsar.bool_sherlokholmes.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by tsar on 25.12.2015.
 */
public class WordsDBHelper extends SQLiteOpenHelper implements BaseColumns{

    public static String DATABASE_NAME = "words.db";
    public static final String WORD_COLUMN = "word";
    private static final String ID = "id";
    private static final String TABLE_NAME = "usersWords";

    public WordsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + TABLE_NAME + " (" + BaseColumns._ID
            + " integer primary key autoincrement, " + WORD_COLUMN
            + " text not null);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_NAME);
        // Создаём новую таблицу
        onCreate(db);
    }


    public String getWords(String id){
        Log.d("DB", "START TO GET FRAGMENT");
        SQLiteDatabase db = this.getReadableDatabase();
        String out = null;
       /* Log.d("DB", "TRY TO GET FRAGMENT");
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID,TEXT}, ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            Log.d("DB", "TRY TO GET STRING");
            out = cursor.getString(1);
            //return cursor.getString(1);
        }  */
        return out;
    }
}
