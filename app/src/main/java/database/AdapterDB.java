package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Anderdavid on 13/08/2016.
 */
public class AdapterDB {

    private static final String TAG = "AdapterDB";

    private static final String DATABASE_NAME ="mobile_skill_testDB";
    private static final int VERSION =4;


    public static final String DATABASE_TABLE_PRODUCTS="products";
    public static final String ID_PRODUCTS ="_id";
    public static final String KEY_PRODUCTO= "producto";

    private static final String DATABASE_CREATE_TABLE_PRODUCTS =
            "create table "+DATABASE_TABLE_PRODUCTS+"("+ID_PRODUCTS+" integer primary key autoincrement," +
                    " "+KEY_PRODUCTO+" text);";



    public static final String DATABASE_TABLE_ITEMS="items";
    public static final String ID_ITEM ="_id";
    public static final String KEY_ITEM= "producto";

    private static final String DATABASE_CREATE_TABLE_ITEMS =
            "create table "+DATABASE_TABLE_ITEMS+"("+ID_ITEM+" integer primary key autoincrement," +
                    " "+KEY_ITEM+" text);";


    public static final String DATABASE_TABLE_PUT_REQUEST="put_request";
    public static final String ID_REQUEST ="_id";
    public static final String KEY_REQUEST= "put_request";

    private static final String DATABASE_CREATE_TABLE_REQUEST =
            "create table "+DATABASE_TABLE_PUT_REQUEST+"("+ID_REQUEST+" integer primary key autoincrement," +
                    " "+KEY_REQUEST+" text);";


    private static  DatabaseHelper mDatabaseHelper;
    private static  SQLiteDatabase mSqLiteDatabase;
    private Context context;






    public AdapterDB(Context context) {
        this.context = context;
        mDatabaseHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
            Log.d(TAG, DATABASE_NAME + " onCreate()");

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE_TABLE_PRODUCTS);
            db.execSQL(DATABASE_CREATE_TABLE_ITEMS);
            db.execSQL(DATABASE_CREATE_TABLE_REQUEST);


            Log.d(TAG, "onCreate table products " + DATABASE_CREATE_TABLE_PRODUCTS);
            Log.d(TAG,"onCreate table items" +DATABASE_CREATE_TABLE_ITEMS);
            Log.d(TAG,"onCreate table items" +DATABASE_CREATE_TABLE_REQUEST);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d(TAG,"onUpgrade");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PRODUCTS);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ITEMS);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PUT_REQUEST);

            onCreate(db);
        }
    }
    public AdapterDB open() throws SQLException{
            mSqLiteDatabase =mDatabaseHelper.getWritableDatabase();

        return  this;
    }

    public void close() throws SQLException {

        mDatabaseHelper.close();
    }


    public void dropDatabase(){
        mSqLiteDatabase.execSQL("DROP DATABASE " + DATABASE_NAME);
    }
    public void dropTable(String table){
        mSqLiteDatabase.execSQL("DROP TABLE " + table);
    }
    public void truncateTable(String table){
        mSqLiteDatabase.execSQL("DELETE FROM " + table);
    }




    public long createProduct(String producto){

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PRODUCTO, producto);

        return mSqLiteDatabase.insert(DATABASE_TABLE_PRODUCTS, null, initialValues);
    }

    public boolean deleteProduct(long id) {
        Log.d(TAG, "delete Product");
        return mSqLiteDatabase.delete(DATABASE_TABLE_PRODUCTS, ID_PRODUCTS + "=" + id, null) > 0;
    }

    public Cursor fetchProduct(String id){
        Cursor mCursor = mSqLiteDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE_PRODUCTS+" WHERE "+ID_PRODUCTS+"='"+id+"'", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllProducts(){
        Cursor mCursor = mSqLiteDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE_PRODUCTS, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }



    public long createItem(String item){

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ITEM,item);

        return mSqLiteDatabase.insert(DATABASE_TABLE_ITEMS, null, initialValues);
    }

    public boolean deleteItem(long id) {
        Log.d(TAG, "delete Product");
        return mSqLiteDatabase.delete(DATABASE_TABLE_ITEMS, ID_ITEM+ "=" + id, null) > 0;
    }

    public Cursor fetchItem(String id){
        Cursor mCursor = mSqLiteDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE_ITEMS+" WHERE "+ID_ITEM+"='"+id+"'", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllItems(){
        Cursor mCursor = mSqLiteDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE_ITEMS, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }


    public long createRequest(String request){

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_REQUEST,request);

        return mSqLiteDatabase.insert(DATABASE_TABLE_PUT_REQUEST, null, initialValues);
    }

    public boolean deleteRequest(long id) {
        Log.d(TAG, "delete Product");
        return mSqLiteDatabase.delete(DATABASE_TABLE_PUT_REQUEST, ID_REQUEST+ "=" + id, null) > 0;
    }

    public Cursor fetchRequest(String id){
        Cursor mCursor = mSqLiteDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE_PUT_REQUEST+" WHERE "+ID_REQUEST+"='"+id+"'", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllRequest(){
        Cursor mCursor = mSqLiteDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE_PUT_REQUEST, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }







}
