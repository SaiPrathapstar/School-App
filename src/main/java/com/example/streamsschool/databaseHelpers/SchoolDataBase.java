package com.example.streamsschool.databaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SchoolDataBase extends SQLiteOpenHelper {

    public SchoolDataBase(Context context) {
        super(context, "class", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists class "+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, YEAR TEXT, BRANCH TEXT, SECTION TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
    public boolean insert(String year , String branch, String section){
        SQLiteDatabase s = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("YEAR", year);
        values.put("BRANCH", branch);
        values.put("SECTION", section);
        long res = s.insert("class", null, values);
        return res != -1;
    }

    public Cursor getClasses(){
        SQLiteDatabase s = this.getWritableDatabase();
        return s.rawQuery("select * from class", null);
    }

    public void update(){}
    public int deleteClass(String year, String branch, String section){
        SQLiteDatabase s = this.getWritableDatabase();
        return s.delete("class", "YEAR = ? AND BRANCH = ? AND SECTION = ?", new String[]{year, branch,section} );
    }
}
