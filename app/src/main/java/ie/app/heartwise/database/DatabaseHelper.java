package ie.app.heartwise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ie.app.heartwise.model.Service;
import ie.app.heartwise.model.User;

/**
 * Created by Ian on 02/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final String DATABASE_NAME = "heartwise";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_USER = "user";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CPASSWORD = "cpassword";

    private static final String CREATE_USER_TABLE = "create table if not exists "
                    + TABLE_USER + "("
                    + COLUMN_NAME + " TEXT PRIMARY KEY, "
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_CPASSWORD + " TEXT);";

    private static final String TABLE_SERVICE = "service";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_CONDITION = "condition";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";


    private static final String CREATE_SERVICE_TABLE = "create table if not exists "
                    + TABLE_SERVICE + "("
                    + COLUMN_ID + " TEXT PRIMARY KEY,"
                    + COLUMN_LOCATION + " TEXT,"
                    + COLUMN_CONDITION + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_TIME + " TEXT,"
                    + COLUMN_LATITUDE + " DECIMAL,"
                    + COLUMN_LONGITUDE + " DECIMAL);";

    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SERVICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE);
        // Create tables again
        onCreate(db);
    }

    public void insertIntoUserTable(User user)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "SELECT * FROM user";
        db.rawQuery(query, null);

        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_CPASSWORD, user.getCpassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void insertIntoServiceTable(Service service)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "SELECT * FROM service";
        db.rawQuery(query, null);

        values.put(COLUMN_ID, service.getID());
        values.put(COLUMN_LOCATION, service.getLocation());
        values.put(COLUMN_CONDITION, service.getCondition());
        values.put(COLUMN_DATE, service.getDate());
        values.put(COLUMN_TIME, service.getTime());
        values.put(COLUMN_LATITUDE, service.getLatitude());
        values.put(COLUMN_LONGITUDE, service.getLongitude());

        db.insert(TABLE_SERVICE, null, values);
        db.close();
    }

    public List<Service> getDataFromServiceTable()
    {
        List<Service> serviceList = new ArrayList<Service>();
        String query = "select * from "+ TABLE_SERVICE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Service service = new Service();
                service.setID(cursor.getString(0));
                service.setLocation(cursor.getString(1));
                service.setCondition(cursor.getString(2));
                service.setDate(cursor.getString(3));
                service.setTime(cursor.getString(4));
                service.setLatitude(cursor.getString(5));
                service.setLongitude(cursor.getString(6));
                serviceList.add(service);

            }while (cursor.moveToNext());
        }
        return serviceList;
    }

    public void updateServiceData(Service service)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "SELECT * FROM service";
        db.rawQuery(query, null);

        values.put(COLUMN_ID, service.getID());
        values.put(COLUMN_LOCATION, service.getLocation());
        values.put(COLUMN_CONDITION, service.getCondition());
        values.put(COLUMN_DATE, service.getDate());
        values.put(COLUMN_TIME, service.getTime());
        values.put(COLUMN_LATITUDE, service.getLatitude());
        values.put(COLUMN_LONGITUDE, service.getLongitude());

        db.update(TABLE_SERVICE, values, COLUMN_ID + "=?", new String[]{String.valueOf(service.getID())});
    }

    public void deleteItem(String id){
        db = this.getWritableDatabase();
        db.delete(TABLE_SERVICE,COLUMN_ID + "=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public String passwordCheck(String username)
    {
        db = this.getReadableDatabase();
        String query = "select username, password from " + TABLE_USER;
        Cursor cursor = db.rawQuery(query, null);

        String correctValue,incorrectValue;
        incorrectValue = "not found";

        if(cursor.moveToFirst())
        {
            do {
                correctValue = cursor.getString(0);

                if(correctValue.equals(username))
                {
                    incorrectValue = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return incorrectValue;
    }
}
