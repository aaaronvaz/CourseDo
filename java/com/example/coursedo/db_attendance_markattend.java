package com.example.coursedo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class db_attendance_markattend extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myd";
    private static final int DATABASE_Version = 1;

    private static final String CLASSROOM_ATTENDANCE_TABLE = "classroom_attendance";
    //private static final String CLASSROOM_MARKED_ATTENDANCE_TABLE = "classroom_marked_attendance";

    private static final String ATTENDANCE_CLASSROOM_ID = "ATTENDANCE_CLASSROOM_ID";
    private static final String ATTENDANCE_ID = "ATTENDANCE_ID";
    private static final String ATTENDANCE_DATE = "ATTENDANCE_DATE";
    private static final String ATTENDANCE_TIME = "ATTENDANCE_TIME";
    private static final String CREATE_CLASSROOM_ATTENDANCE_TABLE = "CREATE TABLE " + CLASSROOM_ATTENDANCE_TABLE +
            " ("+ATTENDANCE_CLASSROOM_ID+" VARCHAR(50)," + ATTENDANCE_ID + " VARCHAR(50)," + ATTENDANCE_DATE + " VARCHAR(20)," + ATTENDANCE_TIME + " VARCHAR(20));";
    private static final String DROP_CLASSROOM_ATTENDANCE_TABLE = "DROP TABLE IF EXISTS " + CLASSROOM_ATTENDANCE_TABLE;

    /*private static final String MARKED_ATTENDANCE_CLASSROOM_ID = "MARKED_ATTENDANCE_CLASSROOM_ID";
    private static final String MARKED_ATTENDANCE_ID = "MARKED_ATTENDANCE_ID";
    private static final String ATTENDANCE_PRESENT_IDS= "ATTENDANCE_PRESENT_IDS";
    private static final String ATTENDANCE_PRESENT_NAMES= "ATTENDANCE_PRESENT_NAMES";
    private static final String CREATE_CLASSROOM_MARKED_ATTENDANCE_TABLE = "CREATE TABLE " + CLASSROOM_ATTENDANCE_TABLE +
            " ("+MARKED_ATTENDANCE_CLASSROOM_ID+" VARCHAR(50)," + MARKED_ATTENDANCE_ID + " VARCHAR(50)," + ATTENDANCE_PRESENT_IDS + " VARCHAR(200)," + ATTENDANCE_PRESENT_NAMES + " VARCHAR(200));";
    private static final String DROP_CLASSROOM_MARKED_ATTENDANCE_TABLE = "DROP TABLE IF EXISTS " + CLASSROOM_MARKED_ATTENDANCE_TABLE;
*/
    private Context context;

    public db_attendance_markattend(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASSROOM_ATTENDANCE_TABLE);
        //db.execSQL(CREATE_CLASSROOM_MARKED_ATTENDANCE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_CLASSROOM_ATTENDANCE_TABLE);
        //db.execSQL(DROP_CLASSROOM_MARKED_ATTENDANCE_TABLE);
        onCreate(db);

    }
    public long addattendance(String c, String s, String date, String time) {
        String id = c + s;
        String aid = date + time;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ATTENDANCE_CLASSROOM_ID, id);
        contentValues.put(ATTENDANCE_ID, aid);
        contentValues.put(ATTENDANCE_DATE, date);
        contentValues.put(ATTENDANCE_TIME, time);
        long res = db.insert(CLASSROOM_ATTENDANCE_TABLE, null, contentValues);
        return res;
    }
    public Cursor getattendance(String c,String s)
    {
        String cid=c+s;
        String[] col={ATTENDANCE_ID,ATTENDANCE_DATE,ATTENDANCE_TIME};
        String[] whereargs={cid};
        SQLiteDatabase dbb=this.getReadableDatabase();
        Cursor cs= dbb.query(CLASSROOM_ATTENDANCE_TABLE,col,ATTENDANCE_CLASSROOM_ID+"=?",whereargs,null,null,null);
        return cs;
    }
    /*public long addmarkedattendance(String c,String s,String attend_id,String stud_id,String stud_name)
    {
        String id=c+s;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(MARKED_ATTENDANCE_CLASSROOM_ID,id);
        contentValues.put(MARKED_ATTENDANCE_ID,attend_id);
        contentValues.put(ATTENDANCE_PRESENT_IDS,stud_id);
        contentValues.put(ATTENDANCE_PRESENT_NAMES,stud_name);
        long res=db.insert(CLASSROOM_MARKED_ATTENDANCE_TABLE,null,contentValues);
        return res;
    }
    public Cursor viewmarkedattendance(String c,String s,String attend_id)
    {
        String id=c+s;
        String[] col={ATTENDANCE_PRESENT_IDS,ATTENDANCE_PRESENT_NAMES};
        String[] where={id,attend_id};
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cs=db.query(CLASSROOM_MARKED_ATTENDANCE_TABLE,col,ATTENDANCE_CLASSROOM_ID+"=?"+" and "+ATTENDANCE_ID+"=?",where,null,null,null);
        return  cs;
    }*/
    public long deleteattendancedata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long res=db.delete(CLASSROOM_ATTENDANCE_TABLE,null,null);
        return res;
    }
}
