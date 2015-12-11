package com.example.hasine.evil_hangman;

import android.content.Context;
import android.content.res.Resources;
import java.util.ArrayList;
import java.util.Random;


public class GoodGameplay implements Gameplay {

    private String currWord;
    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<Integer> index = new ArrayList<>();
    private Context mycontext;

    public GoodGameplay(Context context){
        mycontext = context;
    }

    /**
     * This method reads the data in and decides which word to choose.
     * Returns the current word in mind.
     */
    public String chooseword(int length){
        Resources res = mycontext.getResources();
        String[] allwords = res.getStringArray(R.array.words);

        Random rand = new Random();
        currWord = "";

        for (String allword : allwords)
            if (allword.length() == length) {
                words.add(allword);
            }


        String[] immutable_words = new String[words.size()];
        immutable_words = words.toArray(immutable_words);

//        Define new word and check if it is equal to previous word.
        String newWord = immutable_words[rand.nextInt(words.size())];
        while(newWord.equals(currWord))
            newWord = immutable_words[rand.nextInt(words.size())];

        currWord = newWord;

        return currWord;
    }


    /*
     * This method receives an letterchar and checks if it is guessed right.
     * Returns an integer array index which contains index values,
     * if the guessed letter is in the word.
     */
    public ArrayList LetterChosen(char letterChar) {

        for(int k = 0; k < currWord.length(); k++) {
            if(currWord.charAt(k)==letterChar) {
                index.add(k);
            }
        }

        return index;
    }

}


