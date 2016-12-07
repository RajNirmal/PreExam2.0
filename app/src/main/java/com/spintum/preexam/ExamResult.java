package com.spintum.preexam;
		import java.math.BigDecimal;
		import java.text.DateFormat;
		import java.text.SimpleDateFormat;
		import java.util.Date;
		import java.util.HashMap;
		import java.util.Map;
		import android.app.Activity;
		import android.os.Bundle;
		import android.content.Intent;
		import android.content.SharedPreferences;
		import android.content.SharedPreferences.Editor;
		import android.os.StrictMode;
		import android.support.v7.app.AppCompatActivity;
		import android.support.v7.widget.Toolbar;
		import android.view.Gravity;
		import android.view.Menu;
		import android.view.View;
		import android.view.View.OnClickListener;
		import android.widget.Button;
		import android.widget.ImageView;
		import android.widget.LinearLayout;
		import android.widget.TextView;
		import android.widget.Toast;
		import com.android.volley.AuthFailureError;
		import com.android.volley.Request;
		import com.android.volley.Response;
		import com.android.volley.VolleyError;
		import com.android.volley.toolbox.StringRequest;
public class ExamResult extends AppCompatActivity {
	LinearLayout exres;
	int w, h;
	TextView correct, wrong, unanswered , share;
	TextView percent, ans;
	TextView average, examName, accuracy, finalScore,rescard;
	int skipped = 0;
	Button finish, retake;
	float percentage;
	String[] subject;
	DonutChart chart;
	Toolbar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam_result);
		StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(pol);

	initToolBar();
		int score = 0;
		for (boolean i : Global.answeredCorrect) {
			if (i)
				score++;
		}
		for (boolean i : Global.attended) {
			if (!i)
				skipped++;
		}
		Global.score = "" + score;
		percentage = (((float) score) / Global.noOfQuestions) * 100;

		// wrong,correct,unanswered
		// float values[] = { (float)(100.0-percent-20.0) , (float)percent
		// ,(float)20.0};

		float values[] = { (float) (Global.noOfQuestions - skipped - score),
				(float) score, (float) skipped };
		//float values[] = {5.0f,6.0f,12.0f};

		h = getWindowManager().getDefaultDisplay().getHeight();
		w = getWindowManager().getDefaultDisplay().getWidth();
		//LinearLayout lv1 = (LinearLayout) findViewById(R.id.linear);
		chart = (DonutChart)findViewById(R.id.donutChart);
		correct = (TextView) findViewById(R.id.correct);
		wrong = (TextView) findViewById(R.id.wrong);
		unanswered = (TextView) findViewById(R.id.unanswered);
		this.percent = (TextView) findViewById(R.id.percent);
		ans = (TextView) findViewById(R.id.ans);
		average = (TextView) findViewById(R.id.averageTime);
		//examName = (TextView) findViewById(R.id.examName);
		//total = (TextView) findViewById(R.id.total);
		accuracy = (TextView) findViewById(R.id.accuracy);
		finalScore = (TextView) findViewById(R.id.score);
		//share = (TextView) findViewById(R.id.share);
		finish = (Button) findViewById(R.id.finish);
		retake = (Button) findViewById(R.id.retake);
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		rescard = (TextView) findViewById(R.id.card);
		if(percentage<10) {
			imageView.setImageResource(R.drawable.grad2);
			rescard.setText("Try again, you closer to success");
			rescard.setTextColor(0xff000000);
		} else if(percentage>=10 & percentage<15){
			imageView.setImageResource(R.drawable.grad1);
			rescard.setText("congradulation, you got a success");
			rescard.setTextColor(0xff000000);
		} else if(percentage>=15 & percentage<20) {
			imageView.setImageResource(R.drawable.grad0);
			rescard.setText("wow! OutStanding Performance");
			rescard.setTextColor(0xff000000);
		}
		correct.setTextSize(h / 100);
		wrong.setTextSize(h / 100);
		unanswered.setTextSize(h / 100);
		ans.setTextSize(h / 70);
		this.percent.setTextSize(h / 70);
		//examName.setTextSize(h / 120);
		//total.setTextSize(h / 80);
		accuracy.setTextSize(h / 100);
		//share.setTextSize(h / 100);
		finalScore.setTextSize(h / 100);
		finish.setTextSize(h / 100);
		retake.setTextSize(h / 100);
		chart.getData(score, skipped);
		//retake.setBackground(Global.getGradient(new int[]{0xff536DFE,0xff536DFE,0xff536DFE}));
		//finish.setBackground(Global.getGradient(new int[]{0xff536DFE,0xff536DFE,0xff536DFE}));
		//examName.setBackground(Global.getGradient(new int[]{0xff03A9F4, 0xff03A9F4,0xff03A9F4}));
		//examName.setText(Global.exam+" "+Global.subject);
		correct.setText("Correct :" + score);
		wrong.setText("Wrong : "
				+ (Global.noOfQuestions - score - skipped));
		unanswered.setText("Skipped :"+ skipped);
		//total.setText("Total Questions : " + Global.noOfQuestions + " ( "
		//	+ skipped + " Skipped )");
		if (skipped != Global.noOfQuestions) {
			accuracy.setText("Accuracy: "
					+ BigDecimal
					.valueOf(
							((score / (Global.noOfQuestions - skipped)) * 100))
					.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
		} else {
			accuracy.setText("Accuracy:0.00%");
		}

		this.percent.setText(percentage + "%");
		ans.setText((Global.noOfQuestions - skipped) + "/"
				+ Global.noOfQuestions);
		average.setText("Average Time per question : "
				+ BigDecimal
				.valueOf(
						(((float) Global.answerTime / ((float) Global.noOfQuestions))) / 1000)
				.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue()
				+ " Seconds");
		int wrong = Global.noOfQuestions - skipped - score;
		finalScore.setText("Final Score : " + (score));

		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
				h/3, h/3);
		p.setMargins(80, 150, 30, 30);
		p.gravity = Gravity.CENTER;
		//lv1.setOrientation(LinearLayout.HORIZONTAL);
		//lv1.setLayoutParams(p);
		//values = calculateData(values);
		//DonutChart graphview = new DonutChart(this,values);
		/*share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_TEXT,
						"Friends I recently took a test on preexam app and I scored "
								+ percentage + " % ");
				shareIntent.putExtra(Intent.EXTRA_SUBJECT,
						"PreExam Participated Score.");
				startActivity(Intent.createChooser(shareIntent,
						"Share your score via.."));
			}
		});*/
		retake.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), Exam.class);
				startActivity(i);
				finish();
			}
		});
		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), Home.class);
				startActivity(i);
				finish();
			}
		});

		DateFormat dateFormat = new SimpleDateFormat("dd/MM");
		Date date = new Date();
		Global.date = "" + dateFormat.format(date);
		Global.score = ""+(score);
		Global.maxScore = ""+(Global.noOfQuestions);

		SharedPreferences userscore = getSharedPreferences("UserScore", 0);
		Editor editor = userscore.edit();

		editor.putString("score", Global.score + "," + userscore.getString("score", ""));
		editor.putString("maxScore", Global.maxScore + "," + userscore.getString("maxScore", ""));
		editor.putString("date",Global.date+","+userscore.getString("date",""));
		editor.putString("percent", percentage + "," + userscore.getString("percent", ""));
		editor.clear();
		editor.commit();
		updateSharedPrefs();

	}
	public void initToolBar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle(R.string.title_activity_exam_result);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.drawable.school);
	}
	void updateSharedPrefs(){
		SharedPreferences sharedPreferences = getApplication().getSharedPreferences(VariableHolder.TestSharedPrefName, MODE_APPEND);
		Editor editor = sharedPreferences.edit();
		String CurSub = "";
		Integer UploadSub = -1,SubjectCount = -1;
		try{
			CurSub = sharedPreferences.getString(VariableHolder.CurrentSubject, "No");
			if(CurSub.equals("Maths")) {
				UploadSub = 0;
				SubjectCount = sharedPreferences.getInt(VariableHolder.SPMathsCount , 0);
				SubjectCount++;
				editor.putInt(VariableHolder.SPMathsCount , SubjectCount);
			}else if(CurSub.equals("Physics")) {
				UploadSub = 1;
				SubjectCount = sharedPreferences.getInt(VariableHolder.SPPhysicsCount , 0);
				SubjectCount++;
				editor.putInt(VariableHolder.SPPhysicsCount , SubjectCount);
			}else if(CurSub.equals("Chemistry")) {
				UploadSub = 2;
				SubjectCount = sharedPreferences.getInt(VariableHolder.SPChemistryCount , 0);
				SubjectCount++;
				editor.putInt(VariableHolder.SPChemistryCount , SubjectCount);
			}else if(CurSub.equals("Biology")) {
				UploadSub = 3;
				SubjectCount = sharedPreferences.getInt(VariableHolder.SPBiologyCount , 0);
				SubjectCount++;
				editor.putInt(VariableHolder.SPBiologyCount , SubjectCount);
			}else if(CurSub.equals("CS")) {
				UploadSub = 4;
				SubjectCount = sharedPreferences.getInt(VariableHolder.SPCSCount , 0);
				SubjectCount++;
				editor.putInt(VariableHolder.SPCSCount , SubjectCount);
			}
		}catch (NullPointerException e){
			Toast.makeText(getApplicationContext(),"Please don't Cheat",Toast.LENGTH_SHORT).show();
		}
		StringBuilder sb = new StringBuilder();
		switch (UploadSub){
			case 0:
				sb.append(VariableHolder.SPMaths + "_" + SubjectCount);
			case 1:
				sb.append(VariableHolder.SPPhysics + "_" + SubjectCount);
			case 2:
				sb.append(VariableHolder.SPChemistry + "_" + SubjectCount);
			case 3:
				sb.append(VariableHolder.SPBiology + "_" + SubjectCount);
			case 4:
				sb.append(VariableHolder.SPCS + "_" + SubjectCount);

		}
		sharedPreferences = getSharedPreferences(VariableHolder.UserSharedPrefName,MODE_APPEND);
		String id= sharedPreferences.getString(VariableHolder.SPId , "-1");
		uploadData(sb.toString() , id);
	}
	public void uploadData(final String test_id, final String User_id){
		StringRequest rq = new StringRequest(Request.Method.POST, VariableHolder.TestURL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(getApplicationContext(),volleyError.toString(),Toast.LENGTH_SHORT).show();
					}
				}
		){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map <String,String> params= new HashMap<>();
				params.put(VariableHolder.PTestID ,test_id );
				params.put(VariableHolder.SPId , User_id);
				params.put(VariableHolder.PScore , Global.score);
				return params;
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_exam_result, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(), Home.class);
		startActivity(intent);
		finish();
	}

}

