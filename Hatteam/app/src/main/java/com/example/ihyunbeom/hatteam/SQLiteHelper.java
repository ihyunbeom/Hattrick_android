package com.example.ihyunbeom.hatteam;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by ihyunbeom on 2017-06-22.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sql) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void queryDate(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);

    }

    public void insertData(String topic, String contents){
        SQLiteDatabase database =getWritableDatabase();
        String sql= "INSERT INTO MEMO VALUES(NULL, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, topic);
        statement.bindString(2, contents);


        statement.executeInsert();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }
}
