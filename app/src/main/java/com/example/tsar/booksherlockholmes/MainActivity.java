package com.example.tsar.booksherlockholmes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //TextView text = (TextView)findViewById(R.id.text);
        //Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/ReadytoRide.ttf");
        //text.setTypeface(myFont);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Book.class);
                startActivity(intent);
            }
        });

    }
}
