package com.spintum.preexam;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Menu;
import android.view.View.MeasureSpec;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WithFriends extends Activity {
ListView friends;
TextView overall,t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_with_friends);
		
		this.getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.background));
        this.overridePendingTransition(R.layout.fade_in, R.layout.fade_out);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		
		friends = (ListView)findViewById(R.id.list);
		overall = (TextView)findViewById(R.id.sc);
		t = (TextView)findViewById(R.id.tc);		
		overall.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
		t.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
		
		String[] profile = new String[20];
		String[] score = new String[20];
		String[] time = new String[20];
		for(int i=0 ; i<20 ; i++) {			
			profile[i] = "User "+Character.toString ((char) (i+65));
			time[i] = (float)(2+i)/100+"s";			
			double d = (95-i)+(float)(i+2.00)/100.00;
			score[i] = d+"%";
		}		
		CustomWithFriends adapter=new CustomWithFriends(WithFriends.this, profile,score,time,overall.getMeasuredWidth()-20,t.getMeasuredWidth()-20);
		friends.setAdapter(adapter);

		}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_with_friends, menu);
		return true;
	}
	boolean doubleBackToExitPressedOnce = false;

/*	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}

		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}
*/
}
