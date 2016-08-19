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
public class AdapterDB1 {

    private static final String TAG = "AdapterDB";

    private static final String DATABASE_NAME ="mobile_skill_testDB";
    private static final int VERSION =1;

    private static  DatabaseHelper mDatabaseHelper;
    private static  SQLiteDatabase mSqLiteDatabase;
    private Context context;



    private static final String DATABASE_TABLE_PRODUCTS="products";

    public static final String ID_PRODUCTS ="_id";
    public static final String KEY_CREATED_AT= "createdAt";
    public static final String KEY_IMAGE_URL="imageURL";
    public static final String KEY_NAME="name";
    public static final String KEY_OBJECT_ID="objectId";
    public static final String KEY_PRICE="price";
    public static final String KEY_QUANTITY="quantity";
    public static final String KEY_UPDATE_AT="updatedAt";


    private static final String DATABASE_CREATE_TABLE_PRODUCTS =
            "create table "+DATABASE_TABLE_PRODUCTS+"("+ID_PRODUCTS+" integer primary key autoincrement," +
                    " "+KEY_CREATED_AT+" text," +
                    " "+KEY_IMAGE_URL+" text," +
                    " "+KEY_NAME+" text," +
                    " "+KEY_OBJECT_ID+" text," +
                    " "+KEY_PRICE+" text," +
                    " "+KEY_QUANTITY+" text," +
                    " "+KEY_UPDATE_AT+" text);";


    public AdapterDB1(Context context) {
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
            Log.d(TAG, "onCreate table products " + DATABASE_TABLE_PRODUCTS);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d(TAG,"onUpgrade");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PRODUCTS);
            onCreate(db);
        }
    }
    public AdapterDB1 open() throws SQLException{
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
        mSqLiteDatabase.execSQL("DELETE FROM "+ table);
    }

    public long createProduct(String createdAt, String imageURL, String name, String objectId, String price, String quantity,
            String updatedAt){

        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_CREATED_AT,createdAt);
        initialValues.put(KEY_IMAGE_URL,imageURL);
        initialValues.put(KEY_NAME,name);
        initialValues.put(KEY_OBJECT_ID,objectId);
        initialValues.put(KEY_PRICE,price);
        initialValues.put(KEY_QUANTITY,quantity);
        initialValues.put(KEY_UPDATE_AT, updatedAt);

        return mSqLiteDatabase.insert(DATABASE_TABLE_PRODUCTS, null, initialValues);
    }

    public boolean deleteProduct(long id) {
        Log.d(TAG, "delete Product");
        return mSqLiteDatabase.delete(DATABASE_TABLE_PRODUCTS, ID_PRODUCTS + "=" + id, null) > 0;
    }

    public Cursor fetchAllProducts(String id){
        Cursor mCursor = mSqLiteDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE_PRODUCTS+" WHERE "+ID_PRODUCTS+"='"+id+"'", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }



}
