package com.example.projekt10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WordsDatabaseActivity extends AppCompatActivity
{
    Button btnAdd, btnViewData;
    EditText editText, editTranslation;

    DatabaseHelper mDatabaseHelper;

    Animation animationFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_database);

        editText = (EditText)findViewById(R.id.editTextWord);
        editTranslation = (EditText)findViewById(R.id.editTextTranslation);
        btnAdd = (Button) findViewById(R.id.buttonAddWord);
        btnViewData = (Button) findViewById(R.id.buttonShowWords);
        mDatabaseHelper = new DatabaseHelper(this);

        animationFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fadeout);

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnAdd.startAnimation(animationFadeOut);

                String newEntry = editText.getText().toString();
                String newEntryTranslation = editTranslation.getText().toString();

                //Sprawdzenie czy nie pozostawiono pustych wartości
                if(editText.length() != 0 && editTranslation.length() != 0)
                {
                    //Wywołanie metody dodającej słowa do bazy danych
                    addData(newEntry, newEntryTranslation);
                    editText.setText("");
                    editTranslation.setText("");
                }
                else
                {
                    toastMessage("Pole nie może być puste");
                }
            }
        });
    }

    //Dodanie słów do bazy danych
    public void addData(String newEntry, String newEntryTranslation)
    {
        boolean insertData = mDatabaseHelper.addData(newEntry, newEntryTranslation);

        if (insertData == true)
        {
            toastMessage("Słowo zostało dodane");
        }
        else
        {
            toastMessage("Coś poszło źle");
        }
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showWordsList(View view)
    {
        Intent intent = new Intent(WordsDatabaseActivity.this, ListDataActivity.class);
        startActivity(intent);
        setContentView(R.layout.list_layout);
    }

    public void backToMainActivity(View view)
    {
        Intent intent = new Intent(WordsDatabaseActivity.this, MainActivity.class);
        startActivity(intent);
        setContentView(R.layout.list_layout);
    }
}