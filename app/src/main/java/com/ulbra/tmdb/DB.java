package com.ulbra.tmdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    public static final String DB_NAME = "movies.db";
    public static final String TABLE = "favoritos";
    public static final String ID = "_id";
    public static final String CODE = "code";
    public static final String POSTER = "poster";
    public static final String TITLE = "title";
    public static final String DESC = "desc";
    public static final String POP = "pop";
    public static final int VERSION = 1;

    public DB(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABLE +"("
                + ID + " integer primary key autoincrement,"
                + CODE + " integer,"
                + TITLE + " text,"
                + POSTER + " text,"
                + DESC + " text,"
                + POP + " float"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}