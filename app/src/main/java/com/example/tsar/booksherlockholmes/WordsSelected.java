package com.example.tsar.booksherlockholmes;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tsar.booksherlockholmes.Data.HistoryDBHelper;

/**
 * Created by tsar on 27.02.2016.
 */
public class WordsSelected implements ActionMode.Callback {

    TextView textView;
    Context context;
    public WordsSelected(Context context, TextView textView){
        this.context = context;
        this.textView = textView;
    }
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.context, menu);
        menu.removeItem(android.R.id.selectAll);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int start = textView.getSelectionStart();
        int end = textView.getSelectionEnd();
        SpannableStringBuilder ssb = new SpannableStringBuilder(textView.getText());
        ssb.subSequence(start,end);

        HistoryDBHelper dbHelper = new HistoryDBHelper(context);
        dbHelper.insertWord(ssb.toString(), 0);
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
