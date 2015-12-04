package com.example.hasine.evil_hangman;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Random;

/**
 * Inspired by: http://code.tutsplus.com/series/create-a-hangman-game-for-android--cms-702
 */

public class MainActivityHangman extends AppCompatActivity {

    private ImageView[] bodyParts;
    private int guessLeft;
    private TextView guessletter, guessleft;
    private GridView letters;
    private StringBuilder guessed_letters;
    private TextView[] charViews;
    private String currWord;
    private LinearLayout wordLayout;
    private LetterAdapter ltrAdapt;
    private int numChars;
    private String[] words;
    private Random rand;
    private int currPart;
    private int numParts=6;
    private int numCorr;

    Gameplay gameplay;
    EvilGameplay evilGameplay;
    GoodGameplay goodGameplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_hangman);

        ActionBar actionBar = getActionBar();

        Resources res = getResources();
        words = res.getStringArray(R.array.words);

        rand = new Random();
        currWord = "";

        guessed_letters = new StringBuilder(50);
        guessLeft = 7;


        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView)findViewById(R.id.head);
        bodyParts[1] = (ImageView)findViewById(R.id.body);
        bodyParts[2] = (ImageView)findViewById(R.id.arm1);
        bodyParts[3] = (ImageView)findViewById(R.id.arm2);
        bodyParts[4] = (ImageView)findViewById(R.id.leg1);
        bodyParts[5] = (ImageView)findViewById(R.id.leg2);


        // instantiate all views.
        guessletter = (TextView) findViewById(R.id.guessletter);
        guessleft = (TextView) findViewById(R.id.guessleft);
        wordLayout = (LinearLayout)findViewById(R.id.word);
        letters = (GridView)findViewById(R.id.letters);


        guessleft.setText(getString(R.string.guessleft) + " " + guessLeft);

        for(int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }

        gameplay();

//        goodGameplay = new GoodGameplay();

    }


    public void gameplay(){

        String newWord = words[rand.nextInt(words.length)];

        while(newWord.equals(currWord))
            newWord = words[rand.nextInt(words.length)];

        currWord = newWord;

        charViews = new TextView[currWord.length()];

        currPart=0;
        numChars=currWord.length();
        numCorr=0;

        wordLayout.removeAllViews();


        for (int c = 0; c < currWord.length(); c++) {
            charViews[c] = new TextView(this);
            charViews[c].setText("" + currWord.charAt(c));

            charViews[c].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setTextColor(Color.WHITE);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);

            wordLayout.addView(charViews[c]);
        }

        ltrAdapt=new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);
    }

    public void letterPressed(View view) {
        //user has pressed a letter to guess
        String ltr=((TextView)view).getText().toString();
        char letterChar = ltr.charAt(0);
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.letter_down);

        boolean correct = false;
        for(int k = 0; k < currWord.length(); k++) {
            if(currWord.charAt(k)==letterChar){
                correct = true;
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }

        guessed_letters.append(letterChar + ", ");
        guessletter.setText(getString(R.string.guessletters) + " " + guessed_letters);


        if (correct){
            // do something
        }else if (currPart < numParts) {
            //some guesses left
            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
            guessLeft--;
            guessleft.setText(getString(R.string.guessleft) + " " + guessLeft);
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
                            guessLeft = 7;
                            guessleft.setText(getString(R.string.guessleft) + " " + guessLeft);
                            int len = guessed_letters.length();
                            guessed_letters.delete(0, len);
                            guessletter.setText(getString(R.string.guessletters));
                            MainActivityHangman.this.gameplay();
                        }});

            loseBuild.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivityHangman.this.finish();
                        }});

            loseBuild.show();
        }

        if (numCorr == numChars) {
            // Disable Buttons
            disableBtns();

            // Display Alert Dialog
            AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
            winBuild.setTitle("YAY");
            winBuild.setMessage("You win!\n\nThe answer was:\n\n" + currWord);
            winBuild.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            guessLeft = 7;
                            guessleft.setText(getString(R.string.guessleft) + " " + guessLeft);
                            int len = guessed_letters.length();
                            guessed_letters.delete(0, len);
                            guessletter.setText(getString(R.string.guessletters));
                            MainActivityHangman.this.gameplay();
                        }
                    });

            winBuild.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivityHangman.this.finish();
                        }
                    });

            winBuild.show();
        }
    }

    public void disableBtns() {
        int numLetters = letters.getChildCount();
        for (int l = 0; l < numLetters; l++) {
            letters.getChildAt(l).setEnabled(false);
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
                Intent gotoSetting = new Intent(this, settings.class);
                this.startActivity(gotoSetting);
            case R.id.action_newgame:
                guessLeft = 7;
                int len = guessed_letters.length();
                guessleft.setText(getString(R.string.guessleft) + " " + guessLeft);
                guessed_letters.delete(0, len);
                guessletter.setText(getString(R.string.guessletters));
                MainActivityHangman.this.gameplay();
            case R.id.action_history:
                Intent gotoHistory = new Intent(this, HistoryViewActivity.class);
                this.startActivity(gotoHistory);
            default: return super.onOptionsItemSelected(item);
        }

    }

}
