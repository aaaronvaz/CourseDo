package com.example.coursedo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class db_about_student extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase";
    private static final int DATABASE_Version = 1;

    private static final String CLASSROOM_ABOUT_TABLE = "classroom_about";
    private static final String CLASSROOM_STUDENT_TABLE = "classroom_student";

    private static final String CLASSROOM_ABOUT_ID = "_CLASSROOM_ABOUT_ID";
    private static final String CLASSROOM_ABOUT_DETAILS = "CLASSROOM_ABOUT";
    private static final String CREATE_CLASSROOM_ABOUT_TABLE = "CREATE TABLE " + CLASSROOM_ABOUT_TABLE +
            " (" + CLASSROOM_ABOUT_ID + " VARCHAR(50) PRIMARY KEY," + CLASSROOM_ABOUT_DETAILS + " VARCHAR(1000));";
    private static final String DROP_CLASSROOM_ABOUT_TABLE = "DROP TABLE IF EXISTS " + CLASSROOM_ABOUT_TABLE;

    private static final String STUDENT_CLASSROOM_ID = "CLASSROOM_ID";
    private static final String STUDENT_ID = "STUDENT_ID";
    private static final String STUDENT_FNAME = "STUDENT_FNAME";
    private static final String STUDENT_LNAME = "STUDENT_LNAME";
    private static final String STUDENT_FULLNAME = "STUDENT_FULLNAME";
    private static final String CREATE_CLASSROOM_STUDENT_TABLE = "CREATE TABLE " + CLASSROOM_STUDENT_TABLE +
            " (" + STUDENT_CLASSROOM_ID + " VARCHAR(50)," + STUDENT_ID + " VARCHAR(10)," + STUDENT_FNAME + " VARCHAR(20)," + STUDENT_LNAME + " VARCHAR(20)," + STUDENT_FULLNAME + " VARCHAR(40));";
    private static final String DROP_CLASSROOM_STUDENT_TABLE = "DROP TABLE IF EXISTS " + CLASSROOM_STUDENT_TABLE;

    private Context context;

    public db_about_student(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASSROOM_ABOUT_TABLE);
        db.execSQL(CREATE_CLASSROOM_STUDENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_CLASSROOM_ABOUT_TABLE);
        db.execSQL(DROP_CLASSROOM_STUDENT_TABLE);
        onCreate(db);

    }
    public long addclassroomaboutdetails(String classname, String subname, String details) {
        String id = classname+subname;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLASSROOM_ABOUT_ID, id);
        contentValues.put(CLASSROOM_ABOUT_DETAILS, details);
        long res = db.insert(CLASSROOM_ABOUT_TABLE, null, contentValues);
        return res;
    }
    public Cursor viewabout(String c, String s)
    {
        String id=c+s;
        String[] col={CLASSROOM_ABOUT_DETAILS};
        String[] whereargs={id};
        SQLiteDatabase database=this.getReadableDatabase();

        Cursor cs=database.query(CLASSROOM_ABOUT_TABLE,col,CLASSROOM_ABOUT_ID+"=?",whereargs,null,null,null);
        return cs;
    }
    public long addstudent(String stud_cid, String id, String fname, String lname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(STUDENT_CLASSROOM_ID, stud_cid);
        contentValues.put(STUDENT_ID, id);
        contentValues.put(STUDENT_FNAME, fname);
        contentValues.put(STUDENT_LNAME, lname);
        contentValues.put(STUDENT_FULLNAME, fname + " " + lname);
        long res = db.insert(CLASSROOM_STUDENT_TABLE, null, contentValues);
        return res;
    }
    public Cursor viewstudentsdetails(String c, String s) {
        String id = c + s;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {STUDENT_CLASSROOM_ID, STUDENT_ID, STUDENT_FULLNAME};
        String[] wherearg = {id};
        Cursor cursor = db.query(CLASSROOM_STUDENT_TABLE, cols, STUDENT_CLASSROOM_ID + "=?", wherearg, null, null, null);
        return cursor;
    }
    public Cursor getstudentname(String c, String s, String sid) {
        String id = c + s;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = {STUDENT_FNAME, STUDENT_LNAME};
        String[] where = {id, sid};
        Cursor cursor = db.query(CLASSROOM_STUDENT_TABLE, cols, STUDENT_CLASSROOM_ID + "=?" + " AND " + STUDENT_ID + "=?", where, null, null, null);
        return cursor;
    }
    public long editstudentsnames(String c, String s, String s_id, String fname, String lname) {
        String id = c + s;
        SQLiteDatabase db = this.getWritableDatabase();
        String[] where = {id, s_id};
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_FNAME, fname);
        contentValues.put(STUDENT_LNAME, lname);
        contentValues.put(STUDENT_FULLNAME, fname + " " + lname);
        long res = db.update(CLASSROOM_STUDENT_TABLE, contentValues, STUDENT_CLASSROOM_ID + "=?" + " and " + STUDENT_ID + "=?", where);
        return res;
    }
    public long deletestudent(String c, String s, String sid) {
        String id = c + s;
        SQLiteDatabase db = this.getWritableDatabase();
        String[] where = {id, sid};
        long res = db.delete(CLASSROOM_STUDENT_TABLE, STUDENT_CLASSROOM_ID + "=?" + " and " + STUDENT_ID + "=?", where);
        return res;
    }
    public long deleteaboutdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long res=db.delete(CLASSROOM_ABOUT_TABLE,null,null);
        return res;
    }
    public long deletestudentdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long res=db.delete(CLASSROOM_STUDENT_TABLE,null,null);
        return res;
    }

}
