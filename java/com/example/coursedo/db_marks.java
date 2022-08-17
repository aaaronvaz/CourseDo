package com.example.coursedo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class db_marks extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dtbs";
    private static final int DATABASE_Version = 1;

    private static final String CLASSROOM_ASSESSMENT_TABLE = "classroom_assessment";

    private static final String ASSESSMENT_CLASSROOM_ID = "MARKS_CLASSROOM_ID";
    private static final String ASSESSMENT_TITLE = "ASSESSMENT_TITLE";

    private static final String CREATE_CLASSROOM_ASSESSMENT_TABLE = "CREATE TABLE " + CLASSROOM_ASSESSMENT_TABLE +
            " ("+ASSESSMENT_CLASSROOM_ID+" VARCHAR(50)," + ASSESSMENT_TITLE + " VARCHAR(20));";
    private static final String DROP_CLASSROOM_ASSESSMENT_TABLE = "DROP TABLE IF EXISTS " + CLASSROOM_ASSESSMENT_TABLE;


    private Context context;

    public db_marks(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASSROOM_ASSESSMENT_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_CLASSROOM_ASSESSMENT_TABLE);
        onCreate(db);
    }
    public long addassessment(String c,String s,String assess_title)
    {
        String id=c+s;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(ASSESSMENT_CLASSROOM_ID,id);
        contentValues.put(ASSESSMENT_TITLE,assess_title);
        long res=db.insert(CLASSROOM_ASSESSMENT_TABLE,null,contentValues);
        return res;
    }
    public long deleteassessment(String c,String s,String ass_id)
    {
        String id=c+s;
        SQLiteDatabase db=this.getWritableDatabase();
        String[] where={id,ass_id};
        long res=db.delete(CLASSROOM_ASSESSMENT_TABLE,ASSESSMENT_CLASSROOM_ID+"=?"+" and "+ASSESSMENT_TITLE+"=?",where);
        return res;
    }
    public Cursor viewassessmentinfor(String c,String s)
    {
        String id=c+s;
        SQLiteDatabase db=this.getReadableDatabase();
        String[] cols={ASSESSMENT_TITLE};
        String[] where={id};
        Cursor cursor=db.query(CLASSROOM_ASSESSMENT_TABLE,cols,ASSESSMENT_CLASSROOM_ID+"=?",where,null,null,null);
        return cursor;
    }
    public long deleteassessmentdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long res=db.delete(CLASSROOM_ASSESSMENT_TABLE,null,null);
        return res;
    }
}
