package com.example.hotplace.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hotplace.Data.DBManager;
import com.example.hotplace.models.PlaceInfo;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private final String TAG = "DBManager";
    private static final String DATABASE_NAME="place_manager";
    private static final String TABLE_NAME="place";
    private static final String ID ="id";
    private static final String NAME="name";
    private static final String PHONE="phone";
    private static final String WEB="web";
    private static final String ATTRI="attri";
    private static int VERSON = 1;

    private Context context;


    private String SQLQuery="CREATE TABLE " + TABLE_NAME +" (" +
            ID + " TEXT primary key, "+
            NAME + " TEXT, "+
            PHONE + " TEXT, "+
            WEB + " TEXT, "+
            ATTRI + " TEXT)";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSON);
        this.context=context;
        Log.d(TAG,"DBManage: ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery);
        Log.d(TAG,"onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d(TAG,"onUpdate: ");
    }


    public void addStudent(PlaceInfo placeInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID,placeInfo.getId());
        values.put(NAME,placeInfo.getName());
        values.put(PHONE,placeInfo.getPhoneNumber());
        values.put(WEB,placeInfo.getWebsiteUri());
        values.put(ATTRI,placeInfo.getAttributions());
        db.insert(TABLE_NAME,null, values);
        db.close(); // mo phai dong cho an toan
        Log.d(TAG,"addStudent Successfuly");
    }
    public void removeId(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where ID='" + ID + "'");
        db.close();
        Log.d(TAG,"RemoveID Successfuly");
    }
    public List<PlaceInfo> getAllStudent(){
        List<PlaceInfo> listStudent = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null); //Cursor dung de hung ket qua tra ve
        if (cursor.moveToFirst()){ // phuong thuc nay false la danh sach khong co gi (rong)
            do{
                PlaceInfo placeInfo = new PlaceInfo();
                placeInfo.setId(cursor.getString(0));
                placeInfo.setName(cursor.getString(1));
                placeInfo.setPhoneNumber(cursor.getString(2));
                placeInfo.setWebsiteUri(cursor.getString(3));
                placeInfo.setAttributions(cursor.getString(4));
                listStudent.add(placeInfo);

            }while(cursor.moveToNext()); // sau ket qua co it nhat mot ket qua
        }
        db.close();
        return listStudent;
    }

}
