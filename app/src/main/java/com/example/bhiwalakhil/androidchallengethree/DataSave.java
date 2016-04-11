package com.example.bhiwalakhil.androidchallengethree;

import android.provider.BaseColumns;

/**
 * Created by sandeeprana on 11/04/16.
 */
class DataSave implements BaseColumns {
   public static final String LATITUDE="latitude";
   public static final String LONGITUDE="longitude";
   public static final String TABLE_NAME="geo_locations";
   public static final String TYPE_TEXT=" TEXT ";
   public static final String SQL_CREATE_TABLE ="CREATE TABLE "+DataSave.TABLE_NAME+" (" +
		   ""+DataSave._ID+" INTEGER PRIMARY KEY,"+
		   DataSave.LATITUDE+TYPE_TEXT+","+
		   DataSave.LONGITUDE+ TYPE_TEXT+")"
		   ;
}

