package com.example.hasine.evil_hangman;

/**
 * Created by Hasine on 8-12-2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MyPreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

            SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
            seekBar1.incrementProgressBy(1);
            seekBar1.setMax(15);
            final TextView seekBar1Value = (TextView) findViewById(R.id.textsb1);

            seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    seekBar1Value.setText(String.valueOf(progress));
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
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
                }
            });

            Switch toggle = (Switch) findViewById(R.id.switch1);
            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled

                    } else {
                        // The toggle is disabled
                    }
                }
            });
        }
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
                Intent gotoSetting = new Intent(this, MyPreferencesActivity.class);
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
            default:   return super.onOptionsItemSelected(item);
        }

    }

}