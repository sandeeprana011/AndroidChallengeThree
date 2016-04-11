package com.example.bhiwalakhil.androidchallengethree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
   }

   public void startServiceForListening(View view) {
	  Thread thread=new Thread(new Runnable() {
		 @Override
		 public void run() {
			Intent intent=new Intent(MainActivity.this,InfiniteService.class);
			startService(intent);
		 }
//		 D8:0C:98:6B:61:1D:1A:BC:C4:8B:83:53:B1:1B:21:96:B7:C2:47:A2

	  });
	  thread.start();

   }


   public void startMapService(View view) {
	  Intent intent=new Intent(this,MapActivity.class);
	  startActivity(intent);
   }
}
