package com.spintum.preexam;

/**
 * Created by SAIIVA on 2016-11-23.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class LoginDataBaseAdapter {

    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 3;

    static final String DATABASE_CREATE = "create table " + "LOGIN" +
            "( " + "ID integer primary key autoincrement," + "USERNAME  text," + "PASSWORD text," +
            "REPASSWORD text," + "EMAIL  text," + "MOBILE text," + "LOCATION text,"+"SECURITYHINT text) ";

    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public LoginDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {

        return db;
    }

    public void insertEntry(String getFullName, String getPassword, String getConfirmPassword, String getEmailId, String getLocation, String getMobile, String securityhint)
    {
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", getFullName);
        newValues.put("REPASSWORD", getConfirmPassword);
        newValues.put("PASSWORD", getPassword);
        newValues.put("EMAIL", getEmailId);
        newValues.put("LOCATION",getLocation);
        newValues.put("MOBIlE", getMobile);
        newValues.put("SECURITYHINT", securityhint);


        db.insert("LOGIN", null, newValues);
    }

    public int deleteEntry(String getEmailId) {
        String where = "EMAIL=?";
        int numberOFEntriesDeleted = db.delete("LOGIN", where, new String[]{getEmailId});
        return numberOFEntriesDeleted;
    }

    public String getSinlgeEntry(String getEmailId) {
        Cursor cursor = db.query("LOGIN", null, "EMAIL=?", new String[]{getEmailId}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String getPassword = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return getPassword;
    }
    public String getAllTags(String a) {


        Cursor c = db.rawQuery("SELECT * FROM " + "LOGIN" + " where SECURITYHINT = '" + a + "'", null);
        String str = null;
        if (c.moveToFirst()) {
            do {
                str = c.getString(c.getColumnIndex("PASSWORD"));
            } while (c.moveToNext());
        }
        return str;
    }




    public void updateEntry(String getFullName, String getPassword, String getConfirmPassword, String getEmailId, String getLocation, String getMobile) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("USERNAME", getFullName);
        updatedValues.put("REPASSWORD", getConfirmPassword);
        updatedValues.put("PASSWORD", getPassword);
        updatedValues.put("EMAIL", getEmailId);
        updatedValues.put("LOCATION",getLocation);
        updatedValues.put("MOBIlE", getMobile);
        updatedValues.put("SECURITYHINT", getConfirmPassword);

        String where = "EMAIL = ?";
        db.update("LOGIN", updatedValues, where, new String[]{getEmailId});
    }



}