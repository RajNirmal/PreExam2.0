package com.spintum.preexam;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class PreviousQuestionPapers extends Activity {
	Spinner subject,stclass,year;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previous_question_papers);
		
		this.getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.background));        
        this.overridePendingTransition(R.layout.fade_in, R.layout.fade_out);
		
		subject = (Spinner)findViewById(R.id.subject);
        String[] items = new String[]{"Select Subject","Maths", "Physics", "Chemistry"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        subject.setAdapter(adapter);
        
        stclass = (Spinner)findViewById(R.id.stclass);
        String[] classes = new String[]{"Select Class","12", "11", "10"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classes);
        stclass.setAdapter(adapter2);
        
        year = (Spinner)findViewById(R.id.year);
        String[] years = new String[]{"Select year","2014", "2013", "2012","2011","2010"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        year.setAdapter(adapter3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_previous_question_papers,
				menu);
		return true;
	}
	boolean doubleBackToExitPressedOnce = false;

	/*@Override
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
	}*/

}
