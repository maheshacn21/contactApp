package com.example.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="ContactsDB";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_CONTACT="contacts";
    private  static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_PHONE_NO="phone_no";


    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table "+ TABLE_CONTACT + "("
               + KEY_ID+" integer primary key autoincrement,"
                +KEY_NAME+" text,"
                +KEY_PHONE_NO+" text"+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists "+TABLE_CONTACT);
        onCreate(sqLiteDatabase);

    }

    //********************-----method to add contact to DB----*******************************
    public void addContact(String name,String phone_no){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE_NO,phone_no);
        db.insert(TABLE_CONTACT,null,values);

    }

    //*********************----fetching data from DB----****************************************
    public ArrayList<ContactModel> fetchContact(){
        SQLiteDatabase db=this.getReadableDatabase();
        ArrayList<ContactModel> contactArr=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from "+TABLE_CONTACT+" order by "+KEY_NAME+" asc",null);

        while (cursor.moveToNext()){
            ContactModel model=new ContactModel();
            model.setId(cursor.getInt(0));
            model.setName(cursor.getString(1));
            model.setPhone_no(cursor.getString(2));

            contactArr.add(model);
        }
        return contactArr;
    }

    //*************-----get data by id-----*****************
    public Cursor getDataById(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_CONTACT+" where "+KEY_ID+"="+id,null);
        return cursor;
    }

    public void deleteById(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_CONTACT,KEY_ID+" = ? ",new String[]{String.valueOf(id)});
    }

    public void update(String name,String number,int id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_PHONE_NO,number);

        db.update(TABLE_CONTACT,values,KEY_ID+" =? ",new String[]{String.valueOf(id)});
    }
}
