package com.spintum.preexam;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class BySchool extends Activity {
ListView skool;
RelativeLayout container;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_by_school);
/*
		skool = (ListView)findViewById(R.id.list);
		container = (RelativeLayout)findViewById(R.id.container);
		Display dis = getWindowManager().getDefaultDisplay();
		String[] name1 = {"Jake 90%","William 89%","Harry 88%","Harold 87%"};
		String[] name2 = {"Wayne 86%","John 85%","Finch 84%","Smith 83%"};
		String[] name3 = {"Bruce 82%","Philips 81%","Gates 80%","Steve 79%"};
		CustomBySchool adapter=new CustomBySchool(BySchool.this,name1,name2,name3,dis.getWidth());
		skool.setAdapter(adapter);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_by_school, menu);
		return true;
	}
	boolean doubleBackToExitPressedOnce = false;

	@Override
	public void onBackPressed() {
	/*	if (doubleBackToExitPressedOnce) {
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
	}*/
	}
}
