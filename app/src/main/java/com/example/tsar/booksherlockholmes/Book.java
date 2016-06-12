package com.example.tsar.booksherlockholmes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tsar.booksherlockholmes.Data.ReadDataBase;

public class Book extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView play, pause;
    private SeekBar seekbar;
    private TextView tv_chapter, mainText;
    private ScrollView scrollView;
    private MyMediaPlayer myMediaPlayer;
    private ReadDataBase readDataBase;
    private Words words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_book_nd);

        initializeComponents();
        myMediaPlayer = new MyMediaPlayer(this, seekbar, play, pause);
        readDataBase = new ReadDataBase(this, "SherlockHolmesDB.db", "data.db");

        words= new Words(this);
        words.insertWord("1");
        //words.insertWord("2");
        //words.insertWord("3");
        //words.insertWord("55555");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMediaPlayer.play();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMediaPlayer.pause();
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myMediaPlayer.seekBarChange(fromUser, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        changeText(1);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        myMediaPlayer.activity_pause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        myMediaPlayer.activity_saveIS(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
            myMediaPlayer.activity_RestoreIS(savedInstanceState);
            super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.v("dd", "DESTROY");
    }


    private void initializeComponents() {
        tv_chapter = (TextView) findViewById(R.id.chapter);
        mainText = (TextView) findViewById(R.id.mainText);
        play = (ImageView) findViewById(R.id.play);
        pause = (ImageView) findViewById(R.id.pause);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        seekbar = (SeekBar) findViewById(R.id.seekBar);

        Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/AlexandriaFLF.ttf");
        mainText.setTypeface(myFont);

        mainText.setCustomSelectionActionModeCallback(new WordsSelected(this, mainText));
    }

    public void changeText(final int chapter) {
        tv_chapter.setText(String.valueOf(chapter));
        mainText.setText(ReadDataBase.getText(String.valueOf(chapter)));
        SpannableString ss = new SpannableString("Next chapter");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                changeText(chapter + 1);
            }
        };
        ss.setSpan(clickableSpan, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (chapter < 3) {
            mainText.append(ss);
            mainText.setMovementMethod(LinkMovementMethod.getInstance());
            mainText.setHighlightColor(Color.TRANSPARENT);
        }
        scrollView.scrollTo(0, 0);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.book_nd, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_vocabulary) {
//
//            HistoryDBHelper db = new HistoryDBHelper(this);
//          //  db.insertWord("thee", Integer.parseInt(String.valueOf(tv_chapter.getText())));
//
//            ArrayList<String> list= db.getAllWords();
//            mainText.setText(list.get(0));
//            for (int i = 1; i< list.size(); i++){
//                mainText.append(list.get(i));
//            }


        if (id == R.id.nav_chapter1) {
            changeText(1);

        } else if (id == R.id.nav_chapter2) {
            changeText(2);

        } else if (id == R.id.nav_chapter3) {
            changeText(3);
        } else if(id == R.id.nav_vocabulary){
            startActivity(new Intent(this, Dictionary.class ));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}