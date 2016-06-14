package com.example.tsar.booksherlockholmes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Dictionary extends AppCompatActivity {
    final Uri WORDS_URI = Uri.parse("content://com.example.tsar.booksherlockholmes/words");
    final String WORD = "words";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // get dictionary from db
//        DBHelper db = new DBHelper(this);
//        db.insertWord("th");
//        TextView tv = (TextView) findViewById(R.id.textView);
//            ArrayList<String> list= db.getAllWords();
//            tv.setText(list.get(0));


        Cursor cursor = getContentResolver().query(
                WORDS_URI, null, null, null, null);
        startManagingCursor(cursor);

        String[] from = {"word", "translate"};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_2, cursor, from, to);

        ListView lvWords = (ListView) findViewById(R.id.lvWords);
        lvWords.setAdapter(adapter);

    }
    public void onClickInsert(View v){
        ContentValues cv = new ContentValues();
        String s = "hi";
        String t = new String();
        if(isOnline()) {
            try {
                t = YandexTranslate.translate(s, "en", "ru");
            } catch (IOException e) {
                Log.d("MyLog", "Insert IOException");
                e.printStackTrace();
            } catch (ParseException e) {
                Log.d("MyLog", "Insert ParseException");
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                Log.d("MyLog", "Insert NoSuchAlgorithmException");
                e.printStackTrace();
            } catch (KeyManagementException e) {
                Log.d("MyLog", "Insert KeyManagementException");
                e.printStackTrace();
            } catch (Exception e) {
                Log.d("MyLog", "Insert Exception");
                e.printStackTrace();
            }
        }
        cv.put("word",s);
        cv.put("translate", t);
        Uri newUri = getContentResolver().insert(WORDS_URI, cv);
        Log.d("MyLog", "insert " + newUri.toString());
    }

    //доступен ли интернет
    public boolean isOnline(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
