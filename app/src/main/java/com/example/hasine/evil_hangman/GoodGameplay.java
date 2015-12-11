package com.example.hasine.evil_hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class GoodGameplay implements Gameplay {

    private String currWord;
    private String[] allwords;
    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<Integer> index = new ArrayList<>();
    private Random rand;
    private Context mycontext;

    public GoodGameplay(Context context){
        mycontext = context;
    }

    public String chooseword(int length){
//        Add all of the words in the res array to allwords
        Resources res = mycontext.getResources();
        allwords = res.getStringArray(R.array.words);

        rand = new Random();
        currWord = "";

//        Add words from allwords with chosen length to array words.
        for (int i = 0; i < allwords.length; i++){
            if (allwords[i].length() == length){
                words.add(allwords[i]);
            }
        }

//        Make from words an immutable wordd array.
        String[] wordd = new String[words.size()];
        wordd = words.toArray(wordd);

//        Define new word and check if it is equal to previous word.
        String newWord = wordd[rand.nextInt(words.size())];
        while(newWord.equals(currWord))
            newWord = wordd[rand.nextInt(words.size())];
        currWord = newWord;
        return currWord;
    }


    public ArrayList LetterChosen(char letterChar){
        for(int k = 0; k < currWord.length(); k++) {
            if(currWord.charAt(k)==letterChar){
                index.add(k);
            }
        }
        return index;
    }

}


