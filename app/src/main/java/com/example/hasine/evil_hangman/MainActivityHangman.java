package com.example.hasine.evil_hangman;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Inspired by: http://code.tutsplus.com/series/create-a-hangman-game-for-android--cms-702
 */

public class MainActivityHangman extends AppCompatActivity {

    private ImageView[] bodyParts;
    private TextView[] charViews;
    private TextView guessletter, guessleft;
    private GridView letters;
    private ArrayList<Integer> index = new ArrayList<>();
    private Bundle myInstanceState;
    private StringBuilder guessed_letters;
    private String[] words;
    private String currWord;
    private boolean gameType;
    private int currPart, numParts=6, numCorr, lenword, incorrectguesses;

    EvilGameplay evilGameplay;
    GoodGameplay goodGameplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_hangman);

        myInstanceState = savedInstanceState;

        ActionBar actionBar = getActionBar();

//      To save/read preferences data
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        lenword = SP.getInt("seekBar1Value", 6);
        incorrectguesses = SP.getInt("seekBar2Value", 7);
        gameType = SP.getBoolean("gameType", true);

        guessed_letters = new StringBuilder(50);

        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView)findViewById(R.id.head);
        bodyParts[1] = (ImageView)findViewById(R.id.body);
        bodyParts[2] = (ImageView)findViewById(R.id.arm1);
        bodyParts[3] = (ImageView)findViewById(R.id.arm2);
        bodyParts[4] = (ImageView)findViewById(R.id.leg1);
        bodyParts[5] = (ImageView)findViewById(R.id.leg2);

        for(int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }

        // instantiate all views.
        guessletter = (TextView) findViewById(R.id.guessletter);
        guessleft = (TextView) findViewById(R.id.guessleft);
        guessleft.setText(getString(R.string.guessleft) + " " + incorrectguesses);
        LinearLayout wordLayout = (LinearLayout)findViewById(R.id.word);
        letters = (GridView)findViewById(R.id.letters);


        if (gameType){
            evilGameplay = new EvilGameplay(this);
            currWord = evilGameplay.chooseword(lenword);
        }else{
            goodGameplay = new GoodGameplay(this);
            currWord = goodGameplay.chooseword(lenword);
        }
        Log.d("gameType: ", "" + gameType);
        wordLayout.removeAllViews();

        charViews = new TextView[lenword];

        for (int c = 0; c < lenword; c++) {
            charViews[c] = new TextView(this);
            charViews[c].setText("" + currWord.charAt(c));

            charViews[c].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setTextColor(Color.WHITE);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);

            wordLayout.addView(charViews[c]);
        }

        LetterAdapter ltrAdapt=new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);

        currPart=0;
        numCorr=0;

    }



    public void letterPressed(View view) {
        //user has pressed a letter to guess
        String ltr = ((TextView) view).getText().toString();
        char letterChar = ltr.charAt(0);
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.letter_down);

        Log.d("currWord: ", "" + currWord);
        if (gameType){
            index = evilGameplay.LetterChosen(letterChar);
        }else{
            index = goodGameplay.LetterChosen(letterChar);
        }


        guessed_letters.append(letterChar + ", ");
        guessletter.setText(getString(R.string.guessletters) + " " + guessed_letters);

        if (index.size() != 0) {
            for (int i = 0; i < index.size(); i++) {
                numCorr++;
                charViews[index.get(i)].setTextColor(Color.BLACK);
            }
            index.clear();
        //        if guessed letter is not present in word
        }else if ((currPart < numParts) && (index.size() == 0)) {
            //some guesses left
            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
            incorrectguesses--;
            guessleft.setText(getString(R.string.guessleft) + " " + incorrectguesses);
        }else{
            //user has lost
            disableBtns();

            // Display Alert Dialog
            AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
            loseBuild.setTitle("OOPS");
            loseBuild.setMessage("You lose!\n\nThe answer was:\n\n"+currWord);
            loseBuild.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            recreate();

                        }});

            loseBuild.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivityHangman.this.finish();
                        }});
            loseBuild.show();
        }

        if (numCorr == lenword) {
            // Disable Buttons
            disableBtns();
            Intent gotoWinScreen = new Intent(this, winSCREEN.class);
            startActivity(gotoWinScreen);
        }
    }

    public void disableBtns() {
        int numLetters = letters.getChildCount();
        for (int l = 0; l < numLetters; l++) {
            letters.getChildAt(l).setEnabled(false);
        }
    }


    private void saveSettings(){
        SharedPreferences.Editor SPEditor = getPreferences(Context.MODE_PRIVATE).edit();

        SPEditor.putInt("length word", lenword);
        SPEditor.putInt("incorrect guesses", incorrectguesses);
        SPEditor.putBoolean("gametype", gameType);
        SPEditor.commit();

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
