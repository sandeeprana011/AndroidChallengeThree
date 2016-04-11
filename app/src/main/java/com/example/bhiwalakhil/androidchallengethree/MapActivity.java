package com.example.bhiwalakhil.androidchallengethree;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

   private GoogleMap mMap;
   DBhelper databaseHelper;
   private SQLiteDatabase db;
   LatLng camTarget;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_map);
	  // Obtain the SupportMapFragment and get notified when the map is ready to be used.
	  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
			  .findFragmentById(R.id.map);
	  mapFragment.getMapAsync(this);
	  databaseHelper = new DBhelper(getApplicationContext());
	  db = databaseHelper.getReadableDatabase();
   }


   /**
	* Manipulates the map once available.
	* This callback is triggered when the map is ready to be used.
	* This is where we can add markers or lines, add listeners or move the camera. In this case,
	* we just add a marker near Sydney, Australia.
	* If Google Play services is not installed on the device, the user will be prompted to install
	* it inside the SupportMapFragment. This method will only be triggered once the user has
	* installed Google Play services and returned to the app.
	*/
   @Override
   public void onMapReady(GoogleMap googleMap) {
	  mMap = googleMap;
	  ASyncDAta aSyncDAta = new ASyncDAta();
	  aSyncDAta.execute();

   }

   public PolylineOptions getPolyOptions() {
//	  String selectQuery = "SELECT `"+DataSave.LATITUDE+"`,`"+DataSave.LONGITUDE+"` FROM " + DataSave
	  String selectQuery = "SELECT * FROM " + DataSave
			  .TABLE_NAME;
//	  Cursor c = db.rawQuery(selectQuery, new String[] { DataSave.LATITUDE,DataSave.LONGITUDE });
	  Cursor c = db.rawQuery(selectQuery, null);

	  PolylineOptions rectOptions = new PolylineOptions();
	  if (c.moveToFirst()) {
		 camTarget = new LatLng(
				 Double.parseDouble(c.getString(c.getColumnIndex(DataSave.LATITUDE))),
				 Double.parseDouble(c.getString(c.getColumnIndex(DataSave.LONGITUDE)))
		 );
	  }

	  while (c.moveToNext()) {
		 double latitude = Double.parseDouble(c.getString(c.getColumnIndex(DataSave.LATITUDE)));
		 double longitude = Double.parseDouble(c.getString(c.getColumnIndex(DataSave.LONGITUDE)));
		 rectOptions.add(new LatLng(latitude, longitude));
	  }
	  c.close();

	  rectOptions.width(5)
			  .color(Color.BLUE);
	  return rectOptions;
   }

   class ASyncDAta extends AsyncTask<Void, Void, PolylineOptions> {
	  @Override
	  protected PolylineOptions doInBackground(Void... params) {

		 return getPolyOptions();
	  }

	  @Override
	  protected void onPostExecute(PolylineOptions polylineOptions) {
		 super.onPostExecute(polylineOptions);
		 Polyline polyline = mMap.addPolyline(polylineOptions);
		 CameraPosition cameraPosition = new CameraPosition.Builder()
				 .target(camTarget)      // Sets the center of the map to Mountain View
				 .zoom(18)                   // Sets the zoom
				 .bearing(90)                // Sets the tilt of the camera to 30 degrees
				 .build();                   // Creates a CameraPosition from the builder
		 mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


	  }
   }
}
