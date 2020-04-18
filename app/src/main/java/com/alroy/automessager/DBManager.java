package com.alroy.automessager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.security.PublicKey;

public class DBManager extends SQLiteOpenHelper {

    private static final String TAG="DBManager";
    private static final String dbName="Database.db";
    private static final String Table_name="Messages";
    private static final String COL1 = "ID";
    private static final String COL2 = "messages";


    public DBManager(@Nullable Context context) {
        super(context, Table_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+Table_name+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + "TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addMessage(String message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,message);
        Log.d(TAG,"add data"+message);
        long result =db.insert(Table_name,null,contentValues);

        if(result  == -1)
            return  false;
        else
            return  true;

    }


}
