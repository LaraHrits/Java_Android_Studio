package com.example.lw_5_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDatabase";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_SECOND_NAME = "second_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PHONE = "phone";

    public static final String COLUMN_PHOTO_PATH = "photo path";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_USERS +
                        "(" +
                        COLUMN_ID + " integer primary key, " +
                        COLUMN_FIRST_NAME + " text, " +
                        COLUMN_SECOND_NAME + " text, " +
                        COLUMN_EMAIL + " text, " +
                        COLUMN_ADDRESS + " text, " +
                        COLUMN_PHONE + " text, " +
                        COLUMN_PHOTO_PATH + "text " +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean insertUser(String firstName, String secondName, String email, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, firstName);
        contentValues.put(COLUMN_SECOND_NAME, secondName);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_PHONE, phone);

        db.insert(TABLE_USERS, null, contentValues);
        return true;
    }

    public List<User> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_USERS, null);
        List<User> allUsers = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                User user = new User(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SECOND_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
                );
                allUsers.add(user);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return allUsers;
    }

    public boolean updateUser(Integer id, String firstName, String secondName, String email, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, firstName);
        contentValues.put(COLUMN_SECOND_NAME, secondName);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_PHONE, phone);

        db.update(TABLE_USERS, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteUser(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USERS,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_USERS);
    }

    public void saveAllToSdCard(List<User> userList) {
        StringBuilder allUsersDataString = new StringBuilder();
        if (!userList.isEmpty()) {
            for (User user : userList) {
                allUsersDataString.append(user.getId()).append("\n");
                allUsersDataString.append(user.getFirstName()).append("\n");
                allUsersDataString.append(user.getSecondName()).append("\n");
                allUsersDataString.append(user.getEmail()).append("\n");
                allUsersDataString.append(user.getAddress()).append("\n");
                allUsersDataString.append(user.getPhone()).append("\n");
                allUsersDataString.append("\n");
            }
        } else {
            allUsersDataString.append("DB is empty!");
        }

        try {
            String sFileName = "Users.txt";
            File gpxfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(allUsersDataString);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long addUserWithPhoto(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_SECOND_NAME, user.getSecondName());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_ADDRESS, user.getAddress());
        values.put(COLUMN_PHOTO_PATH, user.getPhotoPath());

        // Вставка запису в таблицю
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

}