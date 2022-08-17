package com.example.coursedo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database_tr_and_classroom extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase";
    private static final int DATABASE_Version = 1;

    private static final String TR_TABLE = "tr_details";
    private static final String CLASSROOM_TABLE = "classroom_details";

    private static final String ID = "_id";
    private static final String FNAME = "FIRST_NAME";
    private static final String LNAME = "LAST_NAME";
    private static final String FULLNAME = "FULL_NAME";
    private static final String EMAILID = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String CREATE_TABLE = "CREATE TABLE " + TR_TABLE +
            " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FNAME + " VARCHAR(255) ," + LNAME + " VARCHAR(15)," + FULLNAME + " VARCHAR(15)," + EMAILID + " VARCHAR(225)," + PASSWORD + " VARCHAR(15));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TR_TABLE;

    private static final String CLASSROOM_NAME = "_CLASSROOMNAME";
    private static final String SUB_NAME = "SUBJECT_NAME";
    private static final String CREATE_CLASSROOM_TABLE = "CREATE TABLE " + CLASSROOM_TABLE +
            " (" + CLASSROOM_NAME + " VARCHAR(25) PRIMARY KEY," + SUB_NAME + " VARCHAR(25));";
    private static final String DROP_CLASSROOM_TABLE = "DROP TABLE IF EXISTS " + CLASSROOM_TABLE;

    private Context context;

    public database_tr_and_classroom(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_CLASSROOM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        db.execSQL(DROP_CLASSROOM_TABLE);
        onCreate(db);
    }
    public long insertdata(String fname, String lname, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FNAME, fname);
        contentValues.put(LNAME, lname);
        contentValues.put(FULLNAME, fname + " " + lname);
        contentValues.put(EMAILID, email);
        contentValues.put(PASSWORD, password);
        long res = db.insert(TR_TABLE, null, contentValues);
        return res;
    }

    public long updatename(String id, String fname, String lname) {
        long res;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FNAME, fname);
        contentValues.put(LNAME, lname);
        contentValues.put(FULLNAME, fname + " " + lname);
        String[] whereArgs = {id};
        res = db.update(TR_TABLE, contentValues, ID + "=?", whereArgs);
        return res;
    }

    public long updatepassword(String id, String password) {
        long res;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, password);
        String[] whereArgs = {id};
        res = db.update(TR_TABLE, contentValues, ID + "=?", whereArgs);
        return res;
    }

    /*public long deleteuser(String id) {
        String[] whereargs = {id};
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(TR_TABLE, ID + "=?", whereargs);
        return res;
    }*/

    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TR_TABLE, null);
        return cursor;
    }

    public String getoldname() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + FULLNAME + " from " + TR_TABLE, null);
        String name;
        if (cursor.getCount() == 0) {
            name = "NAME NOT FOUND:0";
        } else {
            cursor.moveToFirst();
            name = cursor.getString(0);

        }
        return name;
    }

    public String getoldpass() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + PASSWORD + " from " + TR_TABLE, null);
        String name;
        if (cursor.getCount() == 0) {
            name = "NAME NOT FOUND:0";
        } else {
            cursor.moveToFirst();
            name = cursor.getString(0);

        }
        return name;
    }
    public long insertclassroomdetails(String classroomename, String subname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLASSROOM_NAME, classroomename);
        contentValues.put(SUB_NAME, subname);
        long res = db.insert(CLASSROOM_TABLE, null, contentValues);
        return res;
    }

    public long deleteclassroom(String class_name, String subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereargs = {class_name};
        long res = db.delete(CLASSROOM_TABLE, CLASSROOM_NAME + "=?", whereargs);
        return res;
    }

    public int getclasscount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + CLASSROOM_TABLE, null);
        int count = cursor.getCount();
        if (count == 0) {
            return count = 0;
        } else {
            return count;
        }
    }
    public Cursor getclassdetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+CLASSROOM_TABLE, null);
        return cursor;
    }
    public long deletetrdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long res=db.delete(TR_TABLE,null,null);
        return res;
    }
    public long deleteclassroomdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long res=db.delete(CLASSROOM_TABLE,null,null);
        return res;
    }
}
