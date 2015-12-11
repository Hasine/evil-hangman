package com.example.hasine.evil_hangman;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EvilGameplay implements Gameplay {

    private String[] allwords;
    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<Integer> index = new ArrayList<>();
    private List<Character> currWORD = new ArrayList<>();
    private int letterNotPresent = 0;
    private String[] wordd;
    private String[] counterWORDD;
    private Context mycontext;
    private String currWord;

    public EvilGameplay(Context context){
        mycontext = context;
    }

    public String chooseword(int length){
        Resources res = mycontext.getResources();
        allwords = res.getStringArray(R.array.words);

        for (int i = 0; i < allwords.length; i++) {
            if (allwords[i].length() == length) {
                words.add(allwords[i]);
            }
        }

        wordd = new String[words.size()];
        wordd = words.toArray(wordd);

        for (int i = 0; i < length; i++){
            currWORD.add(' ');
        }
        //        Make currWord an immutable string.
        String currWord = new String(String.valueOf(currWORD));

    return currWord;
    }


    public ArrayList LetterChosen(char letterChar){
        HashMap<ArrayList<Integer>, String> hash = new HashMap<>();
        System.out.print(Arrays.toString(wordd));
        for(int j = 0; j < wordd.length; j++) {
            int counter = 0;
            for (int k = 0; k < wordd[j].length(); k++) {
                if (wordd[j].charAt(k) == letterChar) {
                    index.add(k);
                } else {
                    counter++;
                }
            }
            Log.d("index: ", "" + index);
            Log.d("counter: ", "" + counter);

            if (counter == wordd.length){
                index.add(1000);
                hash.put(index, wordd[j]);

            }else if (index != null){
                hash.put(index,wordd[j]);
            }
            index.clear();
            Log.d("indexAfter: ", "" + index);
        }

        ArrayList<Map.Entry<ArrayList<Integer>, String>> copy = new ArrayList<>();
        copy.addAll(hash.entrySet());

        for (Map.Entry<ArrayList<Integer>,String> e : copy) {
            System.out.println(e.getKey() + "..." + e.getValue());
        }



//        Iterator<ArrayList<Integer>> keySetIterator = hash.keySet().iterator();
//
//        while(keySetIterator.hasNext()){
//            ArrayList<Integer> index = keySetIterator.next();
//            String wordslist = hash.get(index);
//            System.out.println("key: " + index + " value: " + wordslist);
//        }


        return index;
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

}
