package com.example.tsar.booksherlockholmes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tsar.booksherlockholmes.Data.WordsDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsar on 25.12.2015.
 */
public class Words {
    WordsDBHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    public Words(Context context){
          databaseHelper = new WordsDBHelper(context);
          sqLiteDatabase = databaseHelper.getWritableDatabase();
    }
    public void insertWord(String word)
    {
        ContentValues newValues = new ContentValues();
        // Задайте значения для каждого столбца
        newValues.put(WordsDBHelper.WORD_COLUMN, word);
        // Вставляем данные в таблицу
        sqLiteDatabase.insert("usersWords", null, newValues);
    }
    public List<String> getWords()
    {
        List<String> words_list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("usersWords", new String[] {WordsDBHelper.WORD_COLUMN},
                null, null,
                null, null, null) ;

        while (cursor.moveToNext()) {
           // int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            words_list.add(cursor.getString(cursor.getColumnIndex(WordsDBHelper.WORD_COLUMN)));
           // Log.i("LOG_TAG", "ROW " + id + " HAS NAME " + name);
        }
        cursor.close();
        return words_list;
    }
}
