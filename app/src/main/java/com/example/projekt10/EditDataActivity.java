package com.example.projekt10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditDataActivity extends AppCompatActivity
{
    Button btnSave, btnDelete;
    EditText editableWord, editableTranslation;
    String selectedName, selectedTranslation;
    int selectedID;

    DatabaseHelper mDatabaseHelper;

    Animation animationBounce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);

        btnSave = (Button) findViewById(R.id.buttonSave);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        editableWord = (EditText) findViewById(R.id.editTextEditedWord);
        editableTranslation = (EditText) findViewById(R.id.editTextEditedTranslation);
        mDatabaseHelper = new DatabaseHelper(this);

        animationBounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_bounce);

        //Pobranie danych z innej aktywności
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1);
        selectedName = receivedIntent.getStringExtra("word");
        selectedTranslation = receivedIntent.getStringExtra("translation");

        //Ustawienie odpowiednich Stringów do pól
        editableWord.setText(selectedName);
        editableTranslation.setText(selectedTranslation);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnSave.startAnimation(animationBounce);

                String word = editableWord.getText().toString();
                String translation = editableTranslation.getText().toString();

                //Jeżeli pola nie są puste
                if(!word.equals("") && (!translation.equals("")))
                {
                    //Aktualizacja słowa w bazie danych
                    mDatabaseHelper.updateName(word, translation, selectedID, selectedName);
                    toastMessage("Zmieniono słowo w bazie danych");
                }
                else
                {
                    toastMessage("Nie możesz pozostawić pustych pól!");
                }
            }
        });

        //Usunięcie słowa z bazy danych
        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDatabaseHelper.deleteName(selectedID);
                editableWord.setText("");
                editableTranslation.setText("");
                toastMessage("Usunięto słowo z bazy danych");

                //Powrót
                Intent intent = new Intent(EditDataActivity.this, MainActivity.class);
                startActivity(intent);
                setContentView(R.layout.list_layout);
            }
        });

    }

    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void backToMainActivity(View view)
    {
        Intent intent = new Intent(EditDataActivity.this, MainActivity.class);
        startActivity(intent);
        setContentView(R.layout.list_layout);
    }

}
