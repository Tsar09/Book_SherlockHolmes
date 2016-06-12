package com.example.tsar.booksherlockholmes.Data;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tsar on 24.12.2015.
 */
public class ReadDataBase {
    private static DatabaseHelper dbHelper;
   // private DatabaseHelper databaseHelper;
    public ReadDataBase(Context context, String db_read, String db_new) {
        try {
            File outFileName = context.getDatabasePath(db_new);

            if (!outFileName.exists()) {
                outFileName.getParentFile().mkdirs();
                Log.v("TAG", "File Not Exist" + outFileName.getName());
                AssetManager am = context.getAssets();
                InputStream in = am.open(db_read);
                Log.v("Tag", "GaaET - copy " + in.toString());
                OutputStream out = new FileOutputStream(outFileName);
                Log.v("TAG", "GET - copy");
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            Log.v("TAG", "filenot found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.v("TAG", "ioexeption");
            e.printStackTrace();
        }
        DatabaseHelper.DATABASE_NAME = db_new;
        Log.v("TAG", "Database is there");
        dbHelper = new DatabaseHelper(context);
    }
    public static String getText(String chapter){
        return dbHelper.getText(chapter);
    }
}
