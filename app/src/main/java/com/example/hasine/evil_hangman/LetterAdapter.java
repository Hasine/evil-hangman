package com.example.hasine.evil_hangman;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.view.View;
import android.view.ViewGroup;

public class LetterAdapter extends BaseAdapter {

    private String[] letters;
    private LayoutInflater letterInf;

    @Override
    public int getCount() {
        return letters.length;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    /*
     * Sets the keyboard input.
     */
    public LetterAdapter(Context c) {
        letters=new String[26];
        for (int a = 0; a < letters.length; a++) {
            letters[a] = "" + (char)(a+'A');
        }

        letterInf = LayoutInflater.from(c);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create a button for the letter at this position in the alphabet
        Button letterBtn;

        if (convertView == null) {

            letterBtn = (Button)letterInf.inflate(R.layout.letter, parent, false);
        } else {
            letterBtn = (Button) convertView;
        }

        letterBtn.setText(letters[position]);
        return letterBtn;
    }

}
