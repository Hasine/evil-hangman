package com.example.hasine.evil_hangman;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HistoryViewActivity extends MainActivityHangman {

    private TextView winner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);

        ActionBar actionBar = getActionBar();

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String winnername = SP.getString("winnername", "");
        Log.d("winnername: ", "" + winnername);
        winner = (TextView) findViewById(R.id.winner);
        winner.setText(winnername);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_hangman, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_history:
                Intent gotoHistory = new Intent(this, HistoryViewActivity.class);
                startActivity(gotoHistory);
                return true;
            case R.id.action_extra_settings:
                Intent gotoSetting = new Intent(this, settings.class);
                startActivity(gotoSetting);
                return true;
            case R.id.action_newgame:
                Intent gotoMain = new Intent(this, MainActivityHangman.class);
                startActivity(gotoMain);
                return true;
            case R.id.action_quitgame:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            default: return super.onOptionsItemSelected(item);
        }

    }

}
