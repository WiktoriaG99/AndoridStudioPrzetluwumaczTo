package com.example.projekt10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
        static final String TABLE_NAME = "words_table";
        static final String COL0 = "ID";
        static final String COL1 = "Word";
        static final String COL2 = "Translation";

    public DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Utworzenie tabeli
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 + " TEXT, " + COL2 + " TEXT);";
        db.execSQL(createTable);

        String addSampleValues1 = "INSERT INTO " + TABLE_NAME + "(" + COL1 + ", " + COL2 + ") VALUES ('cat', 'kot')";
        String addSampleValues2 = "INSERT INTO " + TABLE_NAME + "(" + COL1 + ", " + COL2 + ") VALUES ('dog', 'pies')";
        String addSampleValues3 = "INSERT INTO " + TABLE_NAME + "(" + COL1 + ", " + COL2 + ") VALUES ('parrot', 'papuga')";
        db.execSQL(addSampleValues1);
        db.execSQL(addSampleValues2);
        db.execSQL(addSampleValues3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Dodanie słowa do bazy danych
    public boolean addData(String word, String translation)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, word);
        contentValues.put(COL2, translation);

        //Sprawdzenie czy wystąpił błąd
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //Pobranie wszystkich danych
    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    //Pobranie ID na podstawie słowa (kolumny1)
    public Cursor getItemID(String word)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + word + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Aktualizacja słowa w bazie danych
    public void updateName(String newWord, String newTranslation, int id, String oldWord)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1 +
                " = '" + newWord + "', " + COL2 + " = '" + newTranslation
                + "' WHERE " + COL0 + " = '" + id + "'" +
                " AND " + COL1 + " = '" + oldWord + "'";
        db.execSQL(query);
    }

    //Usunięcie słowa z bazy danych
    public void deleteName(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL0 + " = '" + id + "'";
        db.execSQL(query);
    }

    //Pobranie losowego wiersza
    public Cursor getRandomRow()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String querry ="SELECT * FROM "+ TABLE_NAME + " ORDER BY RANDOM() LIMIT 1";
        Cursor data = db.rawQuery(querry, null);
        return data;
    }

    //Pobranie wiersza
    public Cursor getRow(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String querry ="SELECT * FROM "+ TABLE_NAME + " WHERE "
                + COL0 + " = '" + id + "'";
        Cursor data = db.rawQuery(querry, null);
        return data;
    }

    //Pobranie ID na podstawie słowa (kolumny1)
    public Cursor getItemTranslation(String word)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + word + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
