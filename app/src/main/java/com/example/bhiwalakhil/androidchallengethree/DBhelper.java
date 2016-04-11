package com.example.bhiwalakhil.androidchallengethree;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sandeeprana on 11/04/16.
 */
class DBhelper extends SQLiteOpenHelper {
   public static final int DATABASE_VERSION = 1;
   public static final String DATABASE_NAME = "GEO_LOCATIONS.db";


   public DBhelper(Context context) {
	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
	  db.execSQL(DataSave.SQL_CREATE_TABLE);
   }

   /**
	* For now we are not implementing this method as we are not considering upgradation on
	* database
	* @param db
	* @param oldVersion
	* @param newVersion
	*/
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

   }

}
