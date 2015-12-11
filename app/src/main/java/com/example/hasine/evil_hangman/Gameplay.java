package com.example.hasine.evil_hangman;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public interface Gameplay{

    String chooseword(int length);
    ArrayList LetterChosen(char letterChar);

}