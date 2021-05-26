package com.example.projekt10;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartPractise extends AppCompatActivity
{

    String foreignWord, nativeWord, usersTranslation;
    Button btnCheck, btnRandom;
    EditText editTextUsersTranslation;
    TextView textViewResult, textViewWordToTranslate;

    DatabaseHelper mDatabaseHelper;

    Animation animationFadeIn;
    Animation animationZoomIn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnCheck = (Button) findViewById(R.id.buttonCheck);
        btnRandom = (Button) findViewById(R.id.buttonRandomWord);
        editTextUsersTranslation = (EditText) findViewById(R.id.editTextEnterTranslation);
        textViewResult =  (TextView) findViewById(R.id.textViewResult);
        textViewWordToTranslate = (TextView) findViewById(R.id.textViewWordToTranslate);

        mDatabaseHelper = new DatabaseHelper(this);

        animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fadein);
        animationZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_zoomin);

        //Wylosowanie słowa przy uruchomieniu aktywności
        newWord();
    }

    //Losowanie nowego słowa do przetłumaczenia
    public void newWord()
    {
        //Czyszczenie napisów
        usersTranslation = "";
        editTextUsersTranslation.setText("");
        textViewResult.setText("");

        Cursor data = mDatabaseHelper.getRandomRow();
        while(data.moveToNext())
        {
            //Pobieranie String z kolumny 1
            foreignWord = data.getString(1);
            //Pobieranie String z kolumny 2
            nativeWord = data.getString(2);
        }
        //Wyświetlenie słowa, które użytkownik ma przetłumaczyć
        textViewWordToTranslate.setText(nativeWord);
    }

    public void checkTranslation(View view)
    {
        btnCheck.startAnimation(animationFadeIn);

        //Pobrane wprowadzonego przez użytkownika tłumaczenia
        usersTranslation = editTextUsersTranslation.getText().toString();

        //Porównanie czy tłumaczenie wprowadzone przez użytkownika
        //jest poprawne
        if(usersTranslation.equals(foreignWord))
        {
            textViewResult.setTextColor(Color.rgb(71, 186, 30));
            textViewResult.setText("Dobrze!");
        }
        else
        {
            textViewResult.setTextColor(Color.rgb(173, 29, 19));
            textViewResult.setText("Żle! Poprawna wersja to: " + foreignWord);
        }
    }

    public void randomWord(View view)
    {
        btnRandom.startAnimation(animationZoomIn);
        //Wywołanie metody losującej nowe słowo
        newWord();
    }

    //Powrót i przerwanie ćwiczeń
    public void backToMainActivity(View view)
    {
        Intent intent = new Intent(StartPractise.this, MainActivity.class);
        startActivity(intent);
        setContentView(R.layout.list_layout);
    }
}
