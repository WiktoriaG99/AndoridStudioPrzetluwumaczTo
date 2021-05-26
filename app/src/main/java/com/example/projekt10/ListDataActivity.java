package com.example.projekt10;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity
{
    ListView mListView;
    String translationToSend;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        translationToSend = "";

        populateListView();
    }

    //Uzupełnienie ListView słowami z bazy danych
    private void populateListView()
    {
        Cursor data = mDatabaseHelper.getData();

        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext())
        {
            //Pobieranie String z kolumny 1 i dodawanie do ListView
            listData.add(data.getString(1));
        }

        //Adapter zapewnia dostęp do pozycji danych i jest odpowiedzialny za tworzenie Widoku dla każdego elementu w zestawie danych.
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        //Wybranie słowa z ListView powoduje otwarcie aktywności z możliwością edycji lub usunięcia go
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                String word = adapterView.getItemAtPosition(i).toString();
                //Pobranie ID danego słowa
                Cursor data = mDatabaseHelper.getItemID(word);
                int itemID = -1;
                while (data.moveToNext())
                {
                    itemID = data.getInt(0);
                }

                //Pobranie tłumaczenia danego słowa
                Cursor dataTranslation = mDatabaseHelper.getItemTranslation(word);
                while(dataTranslation.moveToNext())
                {
                    translationToSend = dataTranslation.getString(0);
                }

                //Jeżeli obiekt o danym ID istnieje (czyli jest większe od -1)
                if(itemID > -1)
                {
                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    //Przesłanie danych do innej aktywności
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("word", word);
                    editScreenIntent.putExtra("translation", translationToSend);
                    startActivity(editScreenIntent);
                }
                else
                {
                    toastMessage("Żadne ID nie jest powiązane z danym słowem");
                }
            }
        });
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
