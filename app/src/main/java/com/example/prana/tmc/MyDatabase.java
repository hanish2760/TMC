/*
package com.example.prana.tmc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by prana on 8/6/2017.
 *//*


class MyDatabase extends SQLiteOpenHelper{
    public static final String DATABASENAME="VVT";
    public static final String TABLENAME="TT";
    public static final String TOASTMASTER="Toastmaster";
    public static final String TOPIC="Topic";
    public static final String TIME="Time";
    public static final String ID="ID";
    public MyDatabase(Context context) {
        super(context,DATABASENAME,null,1);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table TT"+"(ID integer primary key,Toastmaster varchar2(20),Topic varchar2(20),Time integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void getInsert(String nme,String tpc,int tym){
        SQLiteDatabase sqlb=getWritableDatabase();
        ContentValues cv=new ContentValues();
        //cv.put(ID,1);
        cv.put(TOASTMASTER,nme);
        cv.put(TOPIC,tpc);
        cv.put(TIME,tym);
        sqlb.insert(TABLENAME,null,cv);
    }
    public List retrieve()
    {
        SQLiteDatabase sql=getReadableDatabase();
        Cursor cur=sql.rawQuery("select *from TT;",null);
        List l=new ArrayList();
        if(cur.moveToFirst())
        {
            do{
                 l.add(cur.getString(1));
                 l.add(cur.getString(2));
                 int temp=cur.getInt(3);
                 int sec=temp%60;
                 int min=temp/60;
                 String time=String.format("%02d",min)+":"+String.format("%02d",sec);
                 l.add(time);
            }while(cur.moveToNext());
        }
        return l;
    }

    public void drop(Context ctxt) {
        ctxt.deleteDatabase("VVT");
    }
}
*/
