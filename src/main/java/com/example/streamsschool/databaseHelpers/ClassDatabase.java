package com.example.streamsschool.databaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClassDatabase extends SQLiteOpenHelper {
    String class_name;
    public ClassDatabase(@Nullable Context context, String name) {
        super(context, name, null, 1);
        this.class_name = name;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+ class_name +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, ROLL TEXT UNIQUE, NAME TEXT, MAIL TEXT, PHONE TEXT, ADDRESS TEXT, PRESENT TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String roll, String name, String mail, String number,String address, String present ){
        SQLiteDatabase s = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ROLL", roll);
        values.put("NAME", name);
        values.put("MAIL", mail);
        values.put("PHONE", number);
        values.put("ADDRESS", address);
        values.put("PRESENT", present);
        long res = s.insert(class_name,null, values );
        return res != -1;
    }

    public Cursor getStudents(){
        SQLiteDatabase s = this.getWritableDatabase();
        return s.rawQuery("select * from "+ class_name, null);
    }

    public Cursor getStudent(String roll){
        SQLiteDatabase s = this.getWritableDatabase();
        String query = "select * from "+class_name+" where ROLL = " + roll;
        return s.rawQuery( query, null);
    }

    public void update( String table_name,String roll , String name, String email, String phone, String address, String present){
        SQLiteDatabase s = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("MAIL", email);
        values.put("PHONE", phone);
        values.put("ADDRESS", address);
        values.put("PRESENT", present);

        s.update(table_name, values, "ROLL = ?", new String[]{roll});
    }
}
