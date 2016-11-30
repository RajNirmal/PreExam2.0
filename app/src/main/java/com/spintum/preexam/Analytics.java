package com.spintum.preexam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.app.FragmentManager;
import android.support.v4.view.LayoutInflaterCompat;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.app.Fragment;

import com.gc.materialdesign.views.ButtonRectangle;


public class Analytics extends Fragment {
	int w, h, dpi, layoutHeight, noOfValues;
	LinearLayout last, header, chart, desc,total;
	TextView percent1, percent2, percent3, tot,takenow;
	RelativeLayout layout;
	int[] manhattan, values;
	Button invite;
	@Override
	public View onCreateView(LayoutInflater layoutInflater,ViewGroup container,Bundle SavedInstances) {

		final View subView=getActivity().getLayoutInflater().inflate(R.layout.activity_analytics, null);

		StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(pol);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		h = displaymetrics.heightPixels;
		w = displaymetrics.widthPixels;
		noOfValues = w / 130;
		layoutHeight = (int) ((float) h * 0.30);
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		dpi = (int) (metrics.density * 160f);
		total=(LinearLayout)subView.findViewById(R.id.totalScore);
		last = (LinearLayout)subView. findViewById(R.id.lastScore);
		header = (LinearLayout) subView.findViewById(R.id.header);
		layout = (RelativeLayout)subView. findViewById(R.id.layout);
		chart = (LinearLayout) subView.findViewById(R.id.chart);
		desc = (LinearLayout)subView. findViewById(R.id.desc);
		percent1 = (TextView)subView. findViewById(R.id.perc1);
		percent2 = (TextView) subView.findViewById(R.id.perc2);
		percent3 = (TextView)subView. findViewById(R.id.perc3);
		takenow = (TextView) subView.findViewById(R.id.takenow);
		invite=(Button)subView.findViewById(R.id.invite);
		takenow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			//	((Home)getActivity()).takeToTest();
			}
		});
		invite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				share();
			}
		});
		data();
		return subView;
	}
	/*void changeActivity(){
		Intent intent=new Intent(Analytics.this,PreQuiz.class);
		startActivity(intent);
	}*/
	public void share(){
		Resources resources = getResources();

		Intent emailIntent = new Intent();
		emailIntent.setAction(Intent.ACTION_SEND);
		// Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
		emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("aa@b.cc"));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My PreExam Score is 90%");
		emailIntent.setType("message/rfc822");

		PackageManager pm = getActivity().getPackageManager();
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setType("text/plain");

		Intent openInChooser = Intent.createChooser(emailIntent, "Email Recepient");

		List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
		List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
		for (int i = 0; i < resInfo.size(); i++) {
			// Extract the label, append it, and repackage it in a LabeledIntent
			ResolveInfo ri = resInfo.get(i);
			String packageName = ri.activityInfo.packageName;
			if(packageName.contains("android.email")) {
				emailIntent.setPackage(packageName);
			} else if(packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
				Intent intent = new Intent();
				intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("text/plain");
				if(packageName.contains("twitter")) {
					intent.putExtra(Intent.EXTRA_TEXT, "My tweet");
				} else if(packageName.contains("facebook")) {
					// Warning: Facebook IGNORES our text. They say "These fields are intended for users to express themselves. Pre-filling these fields erodes the authenticity of the activity_get_user_details voice."
					// One workaround is to use the Facebook SDK to post, but that doesn't allow the activity_get_user_details to choose how they want to share. We can also make a custom landing page, and the link
					// will show the <meta content ="..."> text from that page with our link in Facebook.
					intent.putExtra(Intent.EXTRA_TEXT, "Facebook wall text");
				} else if(packageName.contains("mms")) {
					intent.putExtra(Intent.EXTRA_TEXT, "Wanna try preExam.");
				} else if(packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
					intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("Chooeser"));
					intent.putExtra(Intent.EXTRA_SUBJECT, "Score");
					intent.setType("message/rfc822");
				}

				intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
			}
		}

		// convert intentList to array
		LabeledIntent[] extraIntents = intentList.toArray( new LabeledIntent[ intentList.size() ]);

		openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
		startActivity(openInChooser);
	}

	public void data() {
		SharedPreferences sp=getActivity().getSharedPreferences("UserScore", 0);
		String sc = sp.getString("score", "");
		String pt = sp.getString("percent", "");
		String[] scores = sc.split(",");
		String[] percent = pt.split(",");
		if((!sc.equals(""))){
			float[] per = new float[500];
			if (percent.length >= 0) {
				//tot.setText("" + scores.length);
				String length;Integer totalattempts;
				totalattempts= scores.length;
				length=totalattempts.toString()+" ";
				total.setLayoutParams(new LinearLayout.LayoutParams(w / 4, h / 7));
				total.addView(new PercentCircle(getActivity(), 0, length), 0);
				for (int k = 0; k < percent.length; k++) {
					per[k] = Float.parseFloat(percent[k]);
					percent1.setText(String.format("%.2f", per[2]) + "%");
					percent2.setText(String.format("%.2f", per[1]) + "%");
					percent3.setText(String.format("%.2f", per[0]) + "%");
				}
			}

			int[] values = new int[500];
			if (values.length > 0) {
				for (int i = 0; i < scores.length; i++) {
					values[i] = 0;
				}
				for (int i = 0; i < scores.length; i++) {
					values[i + 1] = Integer.parseInt(scores[i]);
				}
				int[] lscore = new int[10];
				lscore[0] = Integer.parseInt(scores[0]);
				last.removeAllViews();
				last.setLayoutParams(new LinearLayout.LayoutParams(w / 4, h / 7));
				last.addView(new PercentCircle(getActivity(), lscore[0] * 5, scores[0] + " "), 0);
				header.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, h / 5));
				LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w,(int) ((float) h * 0.30));
				p.setMargins(20, 30, 20, 0);
				p.gravity = Gravity.CENTER_HORIZONTAL;
				layout.setGravity(Gravity.CENTER_HORIZONTAL);
				layout.setLayoutParams(p);
				layout.setBackgroundColor(0xffd0d0d0);
				manhattan = new int[noOfValues * 2];
				prepareChart(values);
				chart.animate().rotation(180);
				chart.animate().translationX(100);
				View chart = new chart(getActivity(), manhattan);
				chart.animate().rotation(180);
				// chart.animate().translationX(30);
				layout.addView(chart);
			}
		}
		else {
			total.setLayoutParams(new LinearLayout.LayoutParams(w / 4, h / 7));
			total.addView(new PercentCircle(getActivity(), 0, "0"), 0);
			percent1.setText("0%");
			percent2.setText("0%");
			percent3.setText("0%");
			int[] values = new int[20];
			for (int i = 0; i < 10; i++) {
				values[i] = 0;
			}
			last.removeAllViews();
			last.setLayoutParams(new LinearLayout.LayoutParams(w / 4, h / 7));
			last.addView(new PercentCircle(getActivity(), 0, "0" + ""), 0);
			header.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, h / 5));
			LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w,(int) ((float) h * 0.30));
			p.setMargins(20, 30, 20, 0);
			p.gravity = Gravity.CENTER_HORIZONTAL;
			layout.setGravity(Gravity.CENTER_HORIZONTAL);
			layout.setLayoutParams(p);
			layout.setBackgroundColor(0xffd0d0d0);
			manhattan = new int[noOfValues * 2];
			prepareChart(values);
			chart.animate().rotation(180);
			chart.animate().translationX(100);
			View chart = new chart(getActivity(), manhattan);
			chart.animate().rotation(180);
			// chart.animate().translationX(30);
			layout.addView(chart);
		}
	}
	public class PercentCircle extends TextView {

		Paint mPaint = new Paint();
		int percent;
		String text;

		public PercentCircle(Context context, int percent, String text) {
			super(context);
			this.percent = percent;
			this.text = text;
		}

		@Override
		public void onDraw(Canvas canvas) {

			Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG
					| Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
			mPaint.setDither(true);
			mPaint.setColor(Color.parseColor("#1a237e"));
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeWidth(15);
			// setBackgroundColor(0xff000000);
			int sizeX = getWidth() / 2, sizeY = getHeight() / 2;
			int radius = h / 20;
			int deltaX = sizeX - radius;
			int deltaY = sizeY - radius;
			int arcSizeX = (sizeX - (deltaX / 2)) * 2;
			int arcSizeY = (sizeY - (deltaY / 2)) * 2;
			// Thin circle
			canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2,
					radius, mPaint);
			// Arc
			mPaint.setColor(Color.parseColor("#ffffff"));
			mPaint.setStrokeWidth(15);
			RectF box = new RectF(deltaX, deltaY, arcSizeX, arcSizeY);
			float sweep = 360 * percent * 0.01f;
			canvas.drawArc(box, 0, sweep, false, mPaint);

			mPaint.setTextSize(h / 18);
			mPaint.setColor(0xffffffff);
			mPaint.setStyle(Paint.Style.FILL);
			mPaint.setStrokeWidth(10);
			mPaint.setTextAlign(Paint.Align.RIGHT);
			mPaint.setAntiAlias(true);
			canvas.drawText(text + "", sizeX + (int) ((float) radius * 0.60),
					sizeY + (int) ((float) radius * 0.45), mPaint);
		}

		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			setMeasuredDimension(
					getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
					getDefaultSize(getSuggestedMinimumHeight(),
							heightMeasureSpec));
		}

		protected void onLayout(boolean changed, int left, int top, int right,
								int bottom) {
			MarginLayoutParams margins = MarginLayoutParams.class
					.cast(getLayoutParams());
			margins.topMargin = 45;
			margins.bottomMargin = 45;
			margins.leftMargin = 45;
			margins.rightMargin = 45;
			setLayoutParams(margins);
		};
	}
	public class chart extends View {
		int[] points;

		public chart(Context c, int[] p) {
			super(c);
			points = p;
			setHorizontalScrollBarEnabled(true);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(0xff3f51b5);
			paint.setStrokeWidth(10f);
			paint.setStyle(Paint.Style.STROKE);
			for (int i = 2; i < points.length - 5; i = i + 2) {
				canvas.drawLine(points[i], points[i + 1], points[i + 2],
						points[i + 3], paint);
			}
		}
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
				doubleBackToExitPressedOnce=false;
			}
		}, 2000);
	}*/
	public void prepareChart(int[] values) {
		int max = getMax(values);
		int manPointer = noOfValues * 2 - 1;
		int currentX = 70;
		max += 10;
		int descPointer = noOfValues - 1;

		for (int i = 0; i < noOfValues; i++) {
			double percent = (double) values[i] / (double) max;
			double viewHeight = (double) layoutHeight * percent;
			TextView bar = new TextView(getActivity());
			LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(50,
					(int) viewHeight);
			if(i==5) {
				p.setMargins(90, 0, 80, 0);
			}else if(i==3) {
				p.setMargins(90, 0, 100, 0);
			}else if (i==1)
				p.setMargins(290, 0, 80, 0);

			bar.setLayoutParams(p);
			bar.setBackgroundColor(0xffffffff);
			if(i!=0 && i!=noOfValues-1)
				chart.addView(bar);

			manhattan[manPointer - 1] = currentX;
			currentX += 140;
			manhattan[manPointer] = (int) viewHeight;
			manPointer -= 2;

			TextView tmp = new TextView(getActivity());
			tmp.setText(values[descPointer]+"");
			tmp.setTextSize(w/75);
			if (w > 900){
				tmp.setTextSize(w / 75);
			}
			else {
				if(dpi>160) {
					tmp.setTextSize((int) ((float) w * 0.02));
				}else {
					tmp.setTextSize((int) ((float) w * 0.04));
				}
			}
			if(descPointer==6) {
				tmp.setPadding(105, 0, 0, 0);
			}else {
				tmp.setPadding(120, 0, 0, 0);
			}
			tmp.setTextColor(0xffffffff);
			if(descPointer != noOfValues-1 && descPointer !=0)
				desc.addView(tmp);
			desc.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (int) ((float) h * 0.04)));
			descPointer--;
		}
	}
	public int getMax(int[] arr) {
		int max = 0;
		for (int a : arr) {
			if (a > max)
				max = a;
		}
		return max;
	}

}
