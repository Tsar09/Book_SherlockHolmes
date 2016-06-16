package com.example.tsar.booksherlockholmes;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class YandexTranslate {
    private final static String LOG_TAG = "MyLogs";

    private String apiKey;

    public YandexTranslate(String apiKey){
        this.apiKey = apiKey;
    }

     String translate(String text, String from, String to) {
         String urlStr = null;
         try {
             urlStr = "https://translate.yandex.net/api/v1.5/tr.json/translate"
                     + "?key=" + URLEncoder.encode(apiKey, "UTF-8")
                     + "&text=" + URLEncoder.encode(text, "UTF-8")
                     + "&lang=" + URLEncoder.encode(from + "-" + to, "UTF-8");
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         return getJsonString(urlStr);
    }

    private static String getJsonString(String urlStr)  {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlStr);

            Log.d(LOG_TAG, "OPEN HttpsURLConnection, url: " + url);
            //System.setProperty("http.keepAlive", "false");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int rc = conn.getResponseCode();
            Log.d(LOG_TAG, "conn.getResponseCode() " + rc);
            InputStream stream;
            if (rc == 200) {
                stream = conn.getInputStream();
            } else stream = conn.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null)
                result.append(line);
            return getTranslateFromJson(String.valueOf(result));

        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
          }

    private static String getTranslateFromJson(String str) {

        try {
            JSONParser parser = new JSONParser();

            JSONObject element = (JSONObject) (parser.parse(str));
            StringBuilder sb = new StringBuilder();
            JSONArray array = (JSONArray) element.get("text");
            for (Object s : array) {
                sb.append(s.toString() + "\n");
            }
            return String.valueOf(sb);
        } catch (ParseException ex){
            ex.printStackTrace();
        }
        return null;
    }

}
