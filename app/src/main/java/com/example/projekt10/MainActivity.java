//Wiktoria Gajda - Projekt zaliczeniowy

package com.example.projekt10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ChangeToWordsDatabase(View view)
    {
        Intent intent = new Intent(this, WordsDatabaseActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_words_database);
    }

    public void ChangeToPractise(View view)
    {
        Intent intent = new Intent(this, StartPractise.class);
        startActivity(intent);
        setContentView(R.layout.activity_start);
    }
}