package com.spintum.preexam;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Instructions2 extends AppCompatActivity{
	Button done;
	Toolbar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions2);
		initToolBar();

		done=(Button)findViewById(R.id.done);
		//Toast.makeText(getApplicationContext(), Global.exam+" "+Global.subject, Toast.LENGTH_LONG).show();
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Exam.class);
				startActivity(i);
			}
		});
	}
	public void initToolBar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle(R.string.title_activity_instructions);

		setSupportActionBar(toolbar);

		toolbar.setNavigationIcon(R.drawable.arrow);
		toolbar.setNavigationOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(getApplicationContext(), Home.class);
						startActivity(i);
					}
				}

		);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_instructions, menu);
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
