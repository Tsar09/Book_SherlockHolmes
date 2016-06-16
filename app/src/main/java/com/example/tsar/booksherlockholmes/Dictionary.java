package com.example.tsar.booksherlockholmes;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Dictionary extends AppCompatActivity {
    final Uri WORDS_URI = Uri.parse("content://com.example.tsar.booksherlockholmes/words");
    YandexTranslate translator;

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

    public void onClickInsert(View v) throws Exception {

        new Translate().execute();
    }

    //доступен ли интернет
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private class Translate extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                translator = new YandexTranslate(ApiKeys.YANDEX_API_KEY);
                Thread.sleep(1000);
                translated();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(Dictionary.this, null, "Translating...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progress.dismiss();

            try {
                translated();
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public void translated() {
        ContentValues cv = new ContentValues();
        String translatetotaglog = "sherlock";
        String text2 = new String();
        text2 = translator.translate(translatetotaglog, "en", "ru");

        cv.put("word", translatetotaglog);
        cv.put("translate", text2);
        getContentResolver().insert(WORDS_URI, cv);

    }
}


