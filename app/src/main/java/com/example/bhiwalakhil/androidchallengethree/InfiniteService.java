package com.example.bhiwalakhil.androidchallengethree;

import android.Manifest;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class InfiniteService extends IntentService implements GoogleApiClient
		.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


   private SQLiteDatabase database;
   private GoogleApiClient mGoogleApiClient;
   private Location mLastLocation;


   public InfiniteService() {
	  super("InfiniteService");
   }

   @Override
   public void onCreate() {
	  super.onCreate();

	  // Create an instance of GoogleAPIClient.
	  if (mGoogleApiClient == null) {
		 mGoogleApiClient = new GoogleApiClient.Builder(InfiniteService.this)
				 .addConnectionCallbacks(this)
				 .addOnConnectionFailedListener(this)
				 .addApi(LocationServices.API)
				 .build();
	  }

	  Log.e("started","service started!");

	  mGoogleApiClient.connect();

	  DBhelper dBhelper=new DBhelper(getApplicationContext());
	  database=dBhelper.getWritableDatabase();

   }

   @Override
   protected void onHandleIntent(Intent intent) {

   }

   @Override
   public void onConnected(Bundle bundle) {
	  if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
		 return;
	  }

	  createLocationRequest();
	  mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
			  mGoogleApiClient);
	  if (mLastLocation != null) {

//		 mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//		 mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
	  }
   }

   @Override
   public void onConnectionSuspended(int i) {

   }


   protected void createLocationRequest() {
	  LocationRequest mLocationRequest = new LocationRequest();
	  mLocationRequest.setInterval(10000);
	  mLocationRequest.setFastestInterval(5000);
	  mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	  if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
		 return;
	  }

	  LocationServices.FusedLocationApi.requestLocationUpdates(
			  mGoogleApiClient, mLocationRequest, this);
   }

   @Override
   public void onConnectionFailed(ConnectionResult connectionResult) {
	  Log.e("failed", connectionResult.toString());

   }

   @Override
   public void onLocationChanged(Location location) {
	  // Create a new map of values, where column names are the keys
//	  ContentValues values = new ContentValues();
//	  values.put(DataSave.LATITUDE, location.getLatitude());
//	  values.put(DataSave.LONGITUDE, location.getLongitude());

// Insert the new row, returning the primary key value of the new row
//	  long newRowId;
//	  newRowId =database.insert(
//			  DataSave.TABLE_NAME,
//			  null,
//			  values);


//	  Log.e("Rowid",String.valueOf(newRowId)+"\nlatitude"+String.valueOf(location.getLatitude())
//			  +"\nlongitude"+location.getLongitude());


	  String Rows = "INSERT INTO " + DataSave.TABLE_NAME + " ("
			  + DataSave.LATITUDE + ", " + DataSave.LONGITUDE +") Values ('"+String.valueOf
			  (location.getLatitude()) +"', '"+String.valueOf
			  (location.getLongitude())+"')";

	  Log.e("location","change");
//	  String Rows = "INSERT INTO geo_locations (`latitude`,`longitude`) VALUES(\"la\",\"long\");";

	  database.execSQL(Rows);

	  Log.e("location", "\nlatitude" + String.valueOf(location.getLatitude())
			  + "\nlongitude" + String.valueOf(location.getLongitude()));


   }





}
