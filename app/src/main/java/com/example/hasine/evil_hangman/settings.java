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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class settings extends MainActivityHangman {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getActionBar();

        final SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int lenword = SP.getInt("seekBar1Value", 6);
        int incorrectguesses = SP.getInt("seekBar2Value", 7);
        Boolean gameType = SP.getBoolean("gameType", true);

        SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar1.setProgress(lenword);
        seekBar1.incrementProgressBy(1);
        seekBar1.setMax(15);
        final TextView seekBar1Value = (TextView) findViewById(R.id.textsb1);

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar1, int progress, boolean fromUser) {
                seekBar1Value.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar1) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {
                int sb1value = seekBar1.getProgress();
                SharedPreferences.Editor SPEditor = SP.edit();
                SPEditor.putInt("seekBar1Value", sb1value);
                SPEditor.commit();
            }
        });

        SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar2.setProgress(incorrectguesses);
        seekBar2.incrementProgressBy(1);
        seekBar2.setMax(26);
        final TextView seekBar2Value = (TextView) findViewById(R.id.textsb2);

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {
                seekBar2Value.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar2) {
                int sb2value = seekBar2.getProgress();
                SharedPreferences.Editor SPEditor = SP.edit();
                SPEditor.putInt("seekBar2Value", sb2value);
                SPEditor.commit();
            }
        });

        Switch toggle = (Switch) findViewById(R.id.switch1);
        toggle.setChecked(gameType);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled, Use evil gameplay
                    SharedPreferences.Editor SPEditor = SP.edit();
                    SPEditor.putBoolean("gameType", true);
                    SPEditor.commit();
                } else {
                    // The toggle is disabled, use good gameplay
                    SharedPreferences.Editor SPEditor = SP.edit();
                    SPEditor.putBoolean("gameType", false);
                    SPEditor.commit();
                }
            }
        });



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
            case R.id.action_extra_settings:
                Intent gotoSetting = new Intent(this, settings.class);
                startActivity(gotoSetting);
                return true;
            case R.id.action_newgame:
                Intent gotoMain = new Intent(this, MainActivityHangman.class);
                startActivity(gotoMain);
                return true;
            case R.id.action_history:
                Intent gotoHistory = new Intent(this, HistoryViewActivity.class);
                startActivity(gotoHistory);
                return true;
            case R.id.action_quitgame:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            default:   return super.onOptionsItemSelected(item);
        }

    }
}
