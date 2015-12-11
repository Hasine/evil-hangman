package com.example.hasine.evil_hangman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class winSCREEN extends AppCompatActivity {

    private EditText winnername;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);


        submit = (Button) findViewById(R.id.submit);
        winnername = (EditText) findViewById(R.id.winnername);
    }

    public void submit(View view) {
        String name = String.valueOf(winnername.getText());
        Log.d("name: ", "" + name);
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor SPEditor = SP.edit();
        SPEditor.putString("winnername", name);
        SPEditor.commit();

        Intent gotoHistory = new Intent(this, HistoryViewActivity.class);
        startActivity(gotoHistory);
    }
}
