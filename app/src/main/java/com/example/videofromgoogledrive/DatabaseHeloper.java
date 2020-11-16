package com.example.videofromgoogledrive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class DatabaseHeloper  extends SQLiteOpenHelper {
    public static final String DBNAME = "SIMP";
    public static final String TABLE = "SP";
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "AGE";
    public static final String COL_3 = "ID";

    public DatabaseHeloper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE+" (ID integer primary key autoincrement not null, NAME text, AGE numeric)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public boolean insertData(Bundle bundle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, bundle.getString("name"));
        contentValues.put(COL_2, bundle.getInt("age"));
        long res = db.insert(TABLE, null, contentValues);
        return (res ==-1)? false: true;
    }

    public Cursor allData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("Select NAME, AGE, ID from "+TABLE, null);
        return cur;
    }

    public boolean deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return (db.delete(TABLE, " ID=? ",new String[]{id})<1)? false: true;
    }
}
