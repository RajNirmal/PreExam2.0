package com.spintum.preexam;

import java.io.DataInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Exam3 extends AppCompatActivity implements OnTouchListener, OnClickListener {
	private int noOfQuestions = 20;
	private int i = 0;
	Button img[];
	Toolbar toolbar;
	TextView time;
	WebView question;
	int selected = 1, w, h;
	LinearLayout ll;
	LinearLayout.LayoutParams p;
	WebView[] option = new WebView[4];
	String[] qns = new String[20], ansset = new String[20];
	int correctAnswer;
	int[] answerSelected;
	int[] correct = new int[20];

	Button submit;
	TextView next, previous;

	HorizontalScrollView scroll;
	String temp = "";
	String[] answers = new String[4];

	Button[] ans = new Button[4];
	String path, js;
	RelativeLayout container;
	CountDownTimer cnt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam);

		StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(pol);

		initToolBar();

		for (int l = 0; l < 20; l++)
			ansset[l] = "";
		writeData("http://www.mazelon.com/preexam/preexam/display.php");
		Display dis = getWindowManager().getDefaultDisplay();
		w = dis.getWidth();
		h = dis.getHeight();
		p = new LayoutParams(w / 10, w / 9);
		p.setMargins(7, 0, 0, 0);

		ll = (LinearLayout) findViewById(R.id.map);
		time = (TextView) findViewById(R.id.timer);
		question = (WebView) findViewById(R.id.question);

		submit = (Button) findViewById(R.id.submit);
		next = (TextView) findViewById(R.id.next);
		previous = (TextView) findViewById(R.id.previous);
		scroll = (HorizontalScrollView) findViewById(R.id.scroll);
		container = (RelativeLayout) findViewById(R.id.container);

		option[0] = (WebView) findViewById(R.id.answer1);
		option[1] = (WebView) findViewById(R.id.answer2);
		option[2] = (WebView) findViewById(R.id.answer3);
		option[3] = (WebView) findViewById(R.id.answer4);

		ans[0] = (Button) findViewById(R.id.one);
		ans[1] = (Button) findViewById(R.id.two);
		ans[2] = (Button) findViewById(R.id.three);
		ans[3] = (Button) findViewById(R.id.four);

		RelativeLayout.LayoutParams params[] = new RelativeLayout.LayoutParams[4];
		params[0] = new RelativeLayout.LayoutParams((w / 2) - 40,
				LayoutParams.WRAP_CONTENT);
		params[0].addRule(RelativeLayout.RIGHT_OF, R.id.one1);

		params[1] = new RelativeLayout.LayoutParams((w / 2) - 40,
				LayoutParams.WRAP_CONTENT);
		params[1].addRule(RelativeLayout.RIGHT_OF, R.id.two2);

		params[2] = new RelativeLayout.LayoutParams((w / 2) - 40,
				LayoutParams.WRAP_CONTENT);
		params[2].addRule(RelativeLayout.RIGHT_OF, R.id.three3);

		params[3] = new RelativeLayout.LayoutParams((w / 2) - 40,
				LayoutParams.WRAP_CONTENT);
		params[3].addRule(RelativeLayout.RIGHT_OF, R.id.four4);

		int i = 0;
		for (i = 0; i < 4; i++) {
			ans[i].setOnClickListener(this);
		}

		WebSettings[] webSettings = new WebSettings[5];

		webSettings[0] = question.getSettings();
		for (int n = 1; n <= 4; n++) {
			webSettings[n] = option[n-1].getSettings();
		}
		for (int n = 0; n <= 4; n++) {
			webSettings[n].setJavaScriptEnabled(true);
		}

		i = 0;
		for (WebView k : option) {
			k.setLayoutParams(params[i]);
			k.setOnTouchListener(this);
			i++;
		}
		// question.loadDataWithBaseURL("",js, "text/html", "UTF-8","");
		noOfQuestions = 20;
		Global.noOfQuestions = noOfQuestions;

		img = new Button[noOfQuestions];
		Global.attended = new boolean[noOfQuestions];
		Global.answeredCorrect = new boolean[noOfQuestions];
		answerSelected = new int[noOfQuestions];

		i = 0;

		while (i < noOfQuestions) {
			img[i] = new Button(getApplicationContext());
			img[i].setLayoutParams(p);
			img[i].setGravity(Gravity.CENTER);

			img[i].setId(i);
			img[i].setBackgroundResource(R.drawable.circle);

			if (i + 1 == selected) {
				img[i].setBackgroundResource(R.drawable.filledcircle);
				img[i].setText(i + 1 + "");
				img[i].setTextColor(Color.WHITE);
			} else {
				img[i].setText(i + 1 + "");
				img[i].setTextColor(Color.BLACK);
			}
			img[i].setTextSize(h / 70);
			img[i].setPadding(3, 2, 3, 2);
			ll.addView(img[i]);
			i++;

		}
		question.loadDataWithBaseURL("",
				Global.js + "$x={-b±√{b^2-4ac}}/{2a}$", "text/html", "UTF-8",
				"");
		question.setOnTouchListener(new OnSwipeTouchListener(
				getApplicationContext()) {
			public void onSwipeRight() {
				// left to right,previous question
				if (selected > 1) {
					selected--;
					refresh();
					Animation a = AnimationUtils.loadAnimation(Exam3.this,
							R.anim.left_to_right);
					question.startAnimation(a);
					container.startAnimation(a);
				}
			}

			public void onSwipeLeft() {
				// right to left, next question
				if (selected < Global.noOfQuestions) {
					selected++;
					refresh();
					Animation a = AnimationUtils.loadAnimation(Exam3.this,
							R.anim.right_to_left);
					question.startAnimation(a);
					container.startAnimation(a);
				}

			}
		});
		container.setOnTouchListener(new OnSwipeTouchListener(
				getApplicationContext()) {
			public void onSwipeRight() {
				// left to right,previous question
				if (selected > 1) {
					selected--;
					refresh();
					Animation a = AnimationUtils.loadAnimation(Exam3.this,
							R.anim.left_to_right);
					question.startAnimation(a);
					container.startAnimation(a);
				}
			}

			public void onSwipeLeft() {
				// right to left, next question
				if (selected < Global.noOfQuestions) {
					selected++;
					refresh();
					Animation a = AnimationUtils.loadAnimation(Exam3.this,
							R.anim.right_to_left);
					question.startAnimation(a);
					container.startAnimation(a);
				}

			}
		});

		refresh();

		OnClickListener listener = new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				final int id = v.getId();
				if (selected < id + 1) {
					selected = id + 1;
					refresh();
					Animation a = AnimationUtils.loadAnimation(Exam3.this,
							R.anim.right_to_left);
					question.startAnimation(a);
					container.startAnimation(a);
				} else if (selected > id + 1) {
					selected = id + 1;
					refresh();
					Animation a = AnimationUtils.loadAnimation(Exam3.this,
							R.anim.left_to_right);
					question.startAnimation(a);
					container.startAnimation(a);
				}
			}
		};

		i = 0;
		while (i < noOfQuestions) {
			img[i].setOnClickListener(listener);
			i++;
		}
		int milli = 1000;
		int twenty = 1200;
		int one = twenty / 20;
		Global.totalTime = twenty * milli;
		Global.answerTime = 0;
		cnt = new CountDownTimer(twenty * milli, 1000) {

			public void onTick(long millisUntilFinished) {
				if (((millisUntilFinished / 1000) % 60) < 10) {
					time.setText("Time: "
							+ (millisUntilFinished / 1000) / 60 + ":0"
							+ ((millisUntilFinished / 1000) % 60));
				} else {
					time.setText("Time: "
							+ (millisUntilFinished / 1000) / 60 + ":"
							+ ((millisUntilFinished / 1000) % 60));
				}
				Global.answerTime = Global.totalTime - millisUntilFinished;
			}

			public void onFinish() {
				time.setText("Time Over!");
				Intent i = new Intent(getApplicationContext(), ExamResult.class);
				startActivity(i);
				finish();
			}
		};
		cnt.start();

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean flag = true;
				for (boolean k : Global.attended) {
					if (k != true) {
						flag = false;
						break;
					}
				}
				if (flag) {
					/*
					 * Toast.makeText(getApplicationContext(), "All Answered!",
					 * Toast.LENGTH_LONG).show();
					 */
					Intent i = new Intent(getApplicationContext(),
							ExamResult.class);
					startActivity(i);
					finish();

				} else {
					// Toast.makeText(getApplicationContext(),
					// "Some Not Answered!", Toast.LENGTH_LONG).show();
					AlertDialog.Builder b = new AlertDialog.Builder(Exam3.this);
					b.setTitle("Confirm Submission!");
					b.setMessage("Some Questions are still unanswered!Sure want to submit your exam!");
					b.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
													int arg1) {
									// TODO Auto-generated method stub
									Intent i = new Intent(
											getApplicationContext(),
											ExamResult.class);
									startActivity(i);
									finish();
									cnt.cancel();
								}
							});
					b.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
													int arg1) {
									// TODO Auto-generated method stub

								}
							});
					b.show();
				}
			}
		});
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (selected != noOfQuestions) {
					selected++;
					refresh();
					Animation a = AnimationUtils.loadAnimation(Exam3.this,
							R.anim.right_to_left);
					question.startAnimation(a);
					container.startAnimation(a);
				}
			}
		});
		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (selected != 1) {
					selected--;
					refresh();
					Animation a = AnimationUtils.loadAnimation(Exam3.this,
							R.anim.left_to_right);
					question.startAnimation(a);
					container.startAnimation(a);
				}
			}
		});

	}
	public void initToolBar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle(R.string.title_activity_exam);
		setSupportActionBar(toolbar);

		toolbar.setNavigationIcon(R.drawable.arrow);
		toolbar.setNavigationOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(
								getApplicationContext(),
								Instructions1.class);
						startActivity(i);
						finish();
					}
				}

		);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_exam, menu);
		return true;
	}

	public void refresh() {
		i = 0;
		// String j=
		// "<script>var s = '$ax^2+bx+c=0$ with $a≠0$';M.parseMath(s);document.write(s);</script>'$ax^2+bx+c=0$ with $a≠0$'</body>";
		question.loadDataWithBaseURL("", Global.js + qns[selected - 1]+"</body></html>",
				"text/html", "UTF-8", "");
		correctAnswer = correct[selected - 1];

		if (Global.attended[selected - 1]) {
			// Toast.makeText(getApplicationContext(),(answerSelected[selected-1])+""
			// , Toast.LENGTH_LONG).show();
			switch (answerSelected[selected - 1]) {
				case 1:
					// option1.setChecked(true);
					answered(0);
					break;
				case 2:
					answered(1);
					break;
				case 3:
					answered(2);
					break;
				case 4:
					answered(3);
					break;
			}
		} else {
			// rg.clearCheck();
			for (TextView k : ans) {
				k.setBackground(getResources().getDrawable(R.drawable.outline));
				k.setTextColor(0xff000000);
			}
		}

		while (i < noOfQuestions) {
			// img[i] = new Button(getApplicationContext());
			img[i].setLayoutParams(p);

			if (Global.attended[i]) {
				img[i].setBackground(getResources().getDrawable(
						R.drawable.greencircle));
			} else {
				img[i].setBackground(getResources().getDrawable(
						R.drawable.circle));
			}

			if (i + 1 == selected) {
				img[i].setBackground(getResources().getDrawable(
						R.drawable.filledcircle));
				img[i].setText(i + 1 + "");
				img[i].setTextColor(Color.WHITE);
			} else {
				img[i].setText(i + 1 + "");
				img[i].setTextColor(Color.BLACK);
			}
			// img[i].setTextSize(h / 50);
			// ll.addView(img[i]);
			i++;
		}
		int vLeft = img[selected - 1].getLeft();
		int vRight = img[selected - 1].getRight();
		int sWidth = img[selected - 1].getWidth();
		scroll.smoothScrollTo(((vLeft + vRight - sWidth) / 2), 0);

		answers = ansset[selected - 1].split(",,");
		int j = 0;
		for (String op : answers) {
			option[j].loadDataWithBaseURL("", Global.js + op, "text/html",
					"UTF-8", "");
			j++;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		switch (v.getId()) {
			case R.id.answer1:
				answered(0);
				break;
			case R.id.answer2:
				answered(1);
				break;
			case R.id.answer3:
				answered(2);
				break;
			case R.id.answer4:
				answered(3);
				break;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.one:
				answered(0);
				break;
			case R.id.two:
				answered(1);
				break;
			case R.id.three:
				answered(2);
				break;
			case R.id.four:
				answered(3);
				break;
		}
	}

	public void answered(int option) {
		int i = 0;
		for (i = 0; i < 4; i++) {
			if (i == option) {
				ans[i].setBackground(getResources().getDrawable(
						R.drawable.shadow));
				ans[i].setTextColor(0xffffffff);
			} else {
				ans[i].setBackground(getResources().getDrawable(
						R.drawable.outline));
				ans[i].setTextColor(0xff000000);
			}
		}

		Global.attended[selected - 1] = true;
		answerSelected[selected - 1] = option + 1;
		if (correctAnswer == option) {
			Global.answeredCorrect[selected - 1] = true;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog.Builder b = new AlertDialog.Builder(Exam3.this);
		b.setTitle("Sure Want to quit the exam!");
		b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), Home.class);
				startActivity(intent);
				finish();
			}
		});
		b.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});
		b.show();
	}

	public void writeData(String url) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet post = new HttpGet(url);
			HttpResponse res = httpclient.execute(post);
			HttpEntity entity = res.getEntity();
			String response = EntityUtils.toString(entity,HTTP.UTF_8);

			Pattern p = Pattern.compile("<!DOCTYPE.*</title>", Pattern.DOTALL);
			Matcher m = p.matcher(response);
			response = m.replaceFirst("");

			response = response.replace("\n", "").replace("</body>", "")
					.replace("</html>", "");
			response = response.replace("$$", "$");

			JSONArray array = new JSONArray(response);

			for (int i = 0; i < noOfQuestions; i++) {
				int Min = 0, Max = array.length() - 1;
				int selected = Min + (int) (Math.random() * ((Max - Min) + 1));
				JSONObject resp = array.getJSONObject(selected);
				qns[i] = resp.getString("Questions").replace("$$", "$")
						+ "<br/>";
				for (int k = 0; k < 4; k++) {
					String t = resp.getString("option" + (k + 1));
					ansset[i] += (k != 3) ? t + "  ,,  " : t;
					if (resp.getString("Answer").equals("A"))
						correct[i] = 0;
					if (resp.getString("Answer").equals("B"))
						correct[i] = 1;
					if (resp.getString("Answer").equals("C"))
						correct[i] = 2;
					if (resp.getString("Answer").equals("D"))
						correct[i] = 3;
				}
			}
			// q.write(qdata.getBytes());
			// a.write(ans.getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "exception",
					Toast.LENGTH_LONG).show();
			Log.d("", e.toString());
		}
	}

}
