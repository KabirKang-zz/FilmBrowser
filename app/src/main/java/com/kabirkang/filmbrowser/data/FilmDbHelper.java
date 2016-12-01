package com.kabirkang.filmbrowser.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kabirkang.filmbrowser.data.FilmContract.FilmEntry;


/**
 * Created by kabirkang on 11/27/16.
 */

public class FilmDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "film.db";
    public FilmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FILM_TABLE = "CREATE TABLE " + FilmEntry.TABLE_NAME + " (" +
                FilmEntry._ID + " INTEGER PRIMARY KEY," +
                FilmEntry.COLUMN_DATE + " INTEGER NOT NULL," +
                FilmEntry.COLUMN_FAVORITE + " BOOLEAN NOT NULL," +
                FilmEntry.COLUMN_OVERVIEW + " TEXT NOT NULL," +
                FilmEntry.COLUMN_POSTER + " TEXT NOT NULL," +
                FilmEntry.COLUMN_RATING + " REAL NOT NULL," +
                FilmEntry.COLUMN_TITLE + " TEXT NOT NULL;";

        sqLiteDatabase.execSQL(SQL_CREATE_FILM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FilmContract.FilmEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
