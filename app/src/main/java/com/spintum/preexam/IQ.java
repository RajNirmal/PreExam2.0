package com.spintum.preexam;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class IQ extends Activity {
	WebView iq;
	String path, js, k = "";
	String temp = "";
	TextView s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iq);
		StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(pol);

		this.getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.color.background));
		this.overridePendingTransition(R.layout.fade_in, R.layout.fade_out);

		iq = (WebView) findViewById(R.id.iqqns);
		s = (TextView) findViewById(R.id.s);
		WebSettings settings = iq.getSettings();
		settings.setJavaScriptEnabled(true);

		path = "file:///android_asset/";
		js = "<html><head>" + "<link rel='stylesheet' href='" + path
				+ "jqmath-0.4.0.css'>" + "<script src='" + path
				+ "jquery-1.4.0.min.js'></script>" + "<script src='" + path
				+ "jqmath-etc-0.4.0.min.js'></script>" + "<script src='" + path
				+ "jquery-1.4.3.js'></script>" + "<script src='" + path
				+ "jqmath-0.4.3.js'></script>" + "</head><body>";

		try {
			DataInputStream f = new DataInputStream(getResources()
					.openRawResource(R.raw.question));
			int i = 0;
			try {
				while ((k = f.readLine()) != null) {
					temp += k + "<br/>";
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(getApplicationContext(), "File ! found",
						Toast.LENGTH_LONG).show();
				Log.e("", e.toString());
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "File not found",
					Toast.LENGTH_LONG).show();
		}
		// iq.loadDataWithBaseURL("", js + temp.replace("...", "↖{→}").replace(
		// "root", "√").replace(":", "?")+"\n","text/html", "UTF-8", "");

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet post = new HttpGet(
					"http://www.mazelon.com/preexam/preexam/display.php");
			HttpResponse res = httpclient.execute(post);
			HttpEntity entity = res.getEntity();
			String response = EntityUtils.toString(entity);
			
			Pattern p = Pattern.compile("<!DOCTYPE.*</title>",Pattern.DOTALL);
			Matcher m = p.matcher(response);
			response = m.replaceFirst("");			
			
			response = response.replace("\n", "").replace("</body>", "").replace("</html>", "");
			response = response.replace("$$", "$");	
			
			JSONArray array = new JSONArray(response);			
			String data="";
			JSONArray arr = shuffle(array,20);
			for(int i=0;i<20;i++) {
				int Min =0,Max=array.length();
				//int selected = Min + (int)(Math.random() * ((Max - Min) + 1));
				//JSONObject resp = array.getJSONObject(selected);
				//data += "Question "+(i+1)+":\n"+resp.getString("Questions")+"<br/>";
				JSONObject resp = arr.getJSONObject(i);
				data += "Question "+(i+1)+":\n"+resp.getString("Questions")+"<br/>";
			}			
			iq.loadDataWithBaseURL("",Global.js+data,
					"text/html", "UTF-8", "");
		} catch (Exception e) {
			Log.d("", e.toString());
		}

	}
	public JSONArray shuffle(JSONArray jArray,int numQuestions)throws Exception {
		JSONArray shuffled = new JSONArray();   
	    ArrayList<Integer> intArr1=new ArrayList<Integer>(jArray.length());
	    for(int i = 0; i<jArray.length(); i++){
	        intArr1.add(i);
	    }
	    Collections.shuffle(intArr1);
	    for(int i = 0; i < intArr1.size(); i++){
	        shuffled.put(i, jArray.get(intArr1.get(i)));
	        if (i==(numQuestions-1))
	                break;
	    }
	    return shuffled;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_iq, menu);
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
