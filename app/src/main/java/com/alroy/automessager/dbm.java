package com.alroy.automessager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class dbm  extends SQLiteOpenHelper {


        private static final String dbname="Database.db";
        private static final String Table_name="Message";
        public dbm( Context context) {
            super(context, dbname, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query="create table Message(id integer primary key autoincrement, message text)";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Users");
            onCreate(db);
        }

        public String addmessage(String Message){
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put("message",Message);
//            cv.put("email",Email);
//            cv.put("password",Password);
//            cv.put("picture",Picture);

            long res =db.insert("Message",null,cv);
            Log.d(TAG,"added data"+Message);
            if (res==-1)
                return "Failed";
            else
                return "True";

        }

    public Cursor getMessage(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + Table_name ,null);
        return data;
    }

        public Boolean isLoginValid(String Email, String password){
            String sql="SELECT count (*) from Users where email='" +Email+"' and password='" +password+"'";
            SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
            long l= statement.simpleQueryForLong();
            statement.close();
            if (l==1){
                return true;
            }else {
                return false ;
            }
        }

        public Cursor getAllData(){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor data = db.rawQuery("SELECT * FROM " + Table_name,null);
            return data;
        }

        public Bitmap getimage(String Userid){
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap bt = null;
            Cursor cursor = db.rawQuery(" select * from "+ Table_name +" where email = ?", new String[]{Userid});
            if (cursor.moveToNext()){
                byte[] image= cursor.getBlob(4);
                bt= BitmapFactory.decodeByteArray(image,0,image.length);
                Log.d("blob","bt");
            }
            return  bt;
        }

        public  ArrayList<String> getUserData( String Userid){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(" select * from "+ Table_name +" where email = ?", new String[]{Userid});
            ArrayList<String> queryResult =  new ArrayList<>();
            if (cursor.moveToFirst()){
                String name= cursor.getString(1);
                String email= cursor.getString(2);
                String password= cursor.getString(3);
                queryResult.addAll(Arrays.asList(name, email, password));
            }
            return queryResult;
        }


        public String updateUser(String Name, String Email,byte[] Picture){
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put("name",Name);
            cv.put("picture",Picture);
            long res =db.update("Users",cv,"email=?", new String[]{Email});
            if (res==-1)
                return "Failed";
            else
                return "Sucessfully Updated";
        }

        public String updatePassword(String Userid, String Password){
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put("password",Password);
            long res =db.update("Users",cv,"email=?", new String[]{Userid});
            if (res==-1)
                return "Failed";
            else
                return "Sucessfully Updated Password";
        }


        public Boolean checkDuplicateEmail(String email){
            String sql="SELECT count (*) from Users where email='" +email+"'";
            SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
            long l= statement.simpleQueryForLong();
            statement.close();
            if (l==1){
                return true;
            }else {
                return false ;
            }
        }


    }


