package com.example.promise.med_manager;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Promise on 4/9/2018.
 */

public class DbAdapter {

    //define static variable
    public static int dbversion =11;
    public static String dbname = "ContactsDB";
    public static String dbTable = "contacts";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context,dbname,null, dbversion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS "+dbTable+" (_id INTEGER PRIMARY KEY autoincrement,medName, dosage, medDescription, medFrequency, medTime, medStartDate, medEndDate)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+dbTable);
            onCreate(db);
        }
    }

    //establsh connection with SQLiteDataBase
    private final Context c;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqlDb;

    public DbAdapter(Context context) {
        this.c = context;
    }
    public DbAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(c);
        sqlDb = dbHelper.getWritableDatabase();
        return this;
    }

    //insert data
    public void insert(String text2, String text3, String text4, String text5, String text6, String text7, String text8) {
        //if(!isExist(text3)) {
            sqlDb.execSQL("INSERT INTO contacts (medName,dosage,medDescription,medFrequency,medTime,medStartDate, medEndDate) VALUES('" + text2 + "','" + text3 + "','" + text4 + "','" + text5 + "' ,'" + text6 + "', '" + text7 +"', '"+ text8 +"')");
        //}
    }
    //check entry already in database or not
    /*public boolean isExist(String num){
        String query = "SELECT number FROM contacts WHERE number='"+num+"' LIMIT 1";
        Cursor row = sqlDb.rawQuery(query, null);
        return row.moveToFirst();
    }*/
    //edit data
    public void update(int id, String text2, String text3, String text4, String text5, String text6, String text7, String text8) {
        sqlDb.execSQL("UPDATE "+dbTable+" SET medName='"+text2+"', dosage='"+text3+"', medDescription='"+text4+"', medFrequency='"+text5+"', medTime='"+text6+"', medStartDate='"+text7+"', medEndDate='"+text8+"'  WHERE _id=" + id);
    }

    //delete data
    public void delete(int id) {
        sqlDb.execSQL("DELETE FROM "+dbTable+" WHERE _id="+id);
    }

    //fetch data
    public Cursor fetchAllData() {
        String query = "SELECT * FROM "+dbTable;
        Cursor row = sqlDb.rawQuery(query, null);
        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }

    //fetch data by filter
    public Cursor fetchdatabyfilter(String inputText, String filtercolumn) throws SQLException {
        Cursor row = null;
        String query = "SELECT * FROM "+dbTable;
        if (inputText == null  ||  inputText.length () == 0)  {
            row = sqlDb.rawQuery(query, null);
        }else {
            query = "SELECT * FROM "+dbTable+" WHERE "+filtercolumn+" like '%"+inputText+"%'";
            row = sqlDb.rawQuery(query, null);
        }
        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }
}
