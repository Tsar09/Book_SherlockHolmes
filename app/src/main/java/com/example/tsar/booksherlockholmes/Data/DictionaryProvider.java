package com.example.tsar.booksherlockholmes.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by адинистр on 13.06.2016.
 */
public class DictionaryProvider extends ContentProvider {
    final String LOG_TAG ="MyLog";

    static final String TABLE_NAME = "words";

    static final String COLUMN_WORD = "word";
    static final String COLUMN_TRANSLATE = "translate";
    static final String _ID = "_id";

    static final String AUTHORITY = "com.example.tsar.booksherlockholmes";
    static final String PATH_WORDS = "words";

    //общий Uri
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_WORDS);

    //Типы даннх
    static final String WORDS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + PATH_WORDS;                                        //набор строк

    static final String WORDS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."   //one string
            + AUTHORITY + "." + PATH_WORDS;

    //UriMatcher
    static final int URI_WORDS = 1; //общий uri
    static final int URI_WORDS_ID = 2; //Uri c указанным ID

    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH_WORDS, URI_WORDS);
        uriMatcher.addURI(AUTHORITY, PATH_WORDS + "/#", URI_WORDS_ID);
    }

    DatabaseHelper dbHelper;
    SQLiteDatabase db;


    @Override
    public boolean onCreate() {
        Log.d(LOG_TAG, "onCreate()");
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    //reading
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG,"guery " + uri.toString());
        //check Uri
        switch (uriMatcher.match(uri)){
            case URI_WORDS:         //general uri
                Log.d(LOG_TAG, "URI_WORDS");
                break;
            case URI_WORDS_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_WORDS_ID, " + id);
                // add id to selection condition
                if(TextUtils.isEmpty(selection))
                    selection = _ID + " = " + id;
                else
                    selection = selection + " AND " + _ID + " = " + id;
                break;
            default:
                throw  new IllegalArgumentException("Wrong Uri: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        // ContentResolver will notify about changing data in ,BASE_CONTENT_URI
        cursor.setNotificationUri(getContext().getContentResolver(),BASE_CONTENT_URI);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(LOG_TAG,"getType " + uri.toString());
        switch (uriMatcher.match(uri)){
            case URI_WORDS:
                return WORDS_CONTENT_TYPE;
            case URI_WORDS_ID:
                return WORDS_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(LOG_TAG,"insert " + uri.toString());
        if(uriMatcher.match(uri) != URI_WORDS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(TABLE_NAME, null, values);
        Uri resultUri = ContentUris.withAppendedId(BASE_CONTENT_URI ,rowID);
        // notify ContentProvider that data with address resultUri were changed
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG,"delete " + uri.toString());
        switch (uriMatcher.match(uri)){
            case URI_WORDS:
                Log.d(LOG_TAG, "URI_WORDS");
                break;
            case URI_WORDS_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_WORDS_ID " + id);
                if(TextUtils.isEmpty(selection))
                    selection = _ID + " = " + id;
                else
                    selection = selection + " AND " + _ID + " = " + id;
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int count = db.delete(TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG,"update " + uri.toString());
        switch (uriMatcher.match(uri)){
            case URI_WORDS:
                Log.d(LOG_TAG, "URI_WORDS");
                break;
            case URI_WORDS_ID:
                String id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_WORDS_ID " + id);
                if(TextUtils.isEmpty(selection))
                    selection = _ID + " = " + id;
                else selection = selection + " AND " + _ID + " = " + id;
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int count = db.update(TABLE_NAME, values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }
}
