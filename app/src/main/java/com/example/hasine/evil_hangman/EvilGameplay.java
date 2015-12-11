package com.example.hasine.evil_hangman;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EvilGameplay implements Gameplay {

    private String[] allwords;
    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<Integer> index = new ArrayList<>();
    private List<Character> currWORD = new ArrayList<>();
    private int letterNotPresent = 0;
    private String[] wordds;
    private String[] counterWORDD;
    private Context mycontext;
    private String currWord;


    public EvilGameplay(Context context){
        mycontext = context;
    }

    /**
     * This method is used to read in all the words and to set currword equal to the set length word.
     */
    public String chooseword(int length){
        Resources res = mycontext.getResources();
        allwords = res.getStringArray(R.array.words);

        for (String allword : allwords) {
            if (allword.length() == length) {
                words.add(allword);
            }
        }

        wordds = new String[words.size()];
        wordds = words.toArray(wordds);
        Log.d("words with set length: ", "" + Arrays.toString(wordds));
        for (int i = 0; i < length; i++){
            currWORD.add(' ');
        }
        //        Make currWord an immutable string.
        String currWord = new String(String.valueOf(currWORD));

    return currWord;
    }


    /**
     * Whenever a letter is guessed, this method maps all the words to a hashmap and chooses
     * the largest array and show the guessed letter on the index key which belongs to the
     * largest array.
     */
    public ArrayList LetterChosen(char letterChar){

        HashMap<ArrayList<Integer>, List<String>> hash = new HashMap<>();
        List<String> listnoletter = new ArrayList<>();

        System.out.print(Arrays.toString(wordds));

        for(int j = 0; j < wordds.length; j++) {
            int counter = 0;
            for (int k = 0; k < wordds[j].length(); k++) {
                if (wordds[j].charAt(k) == letterChar) {
                    index.add(k);
                } else {
                    counter++;
                }
            }
            Log.d("index: ", "" + index);
            Log.d("counter: ", "" + counter);

//            if letter is present in word
            if (index.size() != 0) {

                if (hash.isEmpty()) {
                    List<String> newlist = new ArrayList<>();
                    newlist.add(wordds[j]);
                    hash.put(index,newlist);
                }


                Iterator<ArrayList<Integer>> keySetIterator = hash.keySet().iterator();
                while(keySetIterator.hasNext()) {
                    ArrayList<Integer> key = keySetIterator.next();
                    Log.d("key: ", "" + key);

                    if (key == index) {
                        List<String> list = hash.get(key);
                        list.add(wordds[j]);
                        hash.put(key, list);
                    } else {
                        List<String> newlist = new ArrayList<>();
                        newlist.add(wordds[j]);
                        hash.put(index, newlist);
                    }
                }
            }else if (counter == wordds[j].length()) {
                listnoletter.add(wordds[j]);
                hash.put(index, listnoletter);
            }
            index.clear();
        }
        return index;
    }
}



//
////        to find the index of the largest element in counterWORDS
//        counterWORDD = new String[counterWORDS.size()];
//        counterWORDD = counterWORDS.toArray(counterWORDD);
//        int largest = counterWORDD[0], index = 0;
//        for (int i = 1; i < counterWORDD.length(); i++) {
//            if (counterWORDD[i] > largest) {
//                largest = counterWORDD[i];
//                index = i;
//            }
//        }