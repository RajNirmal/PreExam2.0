package com.spintum.preexam;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;

public class MainActivity extends Activity implements TextWatcher {
	Button submit;
	TextView ans;
	TextView log;
	EditText username, email, pass, SQ1,SQ1a,SQ2,SQ2a;
	int success = 0;
	boolean[] flag=new boolean[3];
	TextView login;
	SharedPreferences Log_Details;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_user_details);
		StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(pol);
		this.overridePendingTransition(R.layout.fade_in, R.layout.fade_out);
		View decorView = getWindow().getDecorView();
// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
		Log_Details = getApplicationContext().getSharedPreferences(VariableHolder.UserSharedPrefName, MODE_PRIVATE);
		editor = Log_Details.edit();
		log = (TextView) findViewById(R.id.login);
		username = (EditText) findViewById(R.id.name);
		email = (EditText) findViewById(R.id.umail);
		pass = (EditText) findViewById(R.id.pass);
		SQ1 = (EditText) findViewById(R.id.Q1);
		SQ1a = (EditText) findViewById(R.id.Qa1);
		SQ2 = (EditText) findViewById(R.id.Q2);
		SQ2a = (EditText) findViewById(R.id.Qa2);
		submit = (Button) findViewById(R.id.submit_user);
		ans=(TextView)findViewById(R.id.error);
		log.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), Login.class);
				startActivity(i);
				finish();
			}
		});
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean check = verifyAllFields();
				if (!check) {
					AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
					// Setting Dialog Title
					alertDialog.setTitle("Wrong Input");
					// Setting Dialog Message
					alertDialog.setMessage("Please enter all the fields");
					// Setting OK Button
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog closed
							//        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
							pass.setText("");
						}
					});
					// Showing Alert Message
					alertDialog.show();
				} else {
					String SPUserName, SPUserMail, SPUserPass;
					SPUserName = username.getText().toString();
					SPUserMail = email.getText().toString();
					SPUserPass = pass.getText().toString();
					editor.putString(VariableHolder.SPName, SPUserName);
					editor.putString(VariableHolder.SPmail, SPUserMail);
					editor.putString(VariableHolder.SPpass, SPUserPass);
					//editor.putString(VariableHolder.SPId,"2345465434");
					editor.commit();
					goToUserDetails();

					EnterUser();
					//getID();
				}

			}
		});
	}

	/*void getID(){

    	StringRequest str=new StringRequest(Request.Method.POST, VariableHolder.ReturnIDURL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						//Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonobject = new JSONObject(s);
							String id = jsonobject.getString("Account_No");
                            Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
                            //ans.setText(jsonobject.toString());
                          //  Toast.makeText(getApplication(),"Working",Toast.LENGTH_SHORT).show();;
                        }catch(JSONException e){
                            e.printStackTrace();
                            //ans.setText(jsonobject.toString());
                        }
                    }
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(getApplicationContext(), volleyError.toString(),Toast.LENGTH_LONG).show();
					}
				}
		){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String,String> params=new HashMap<>();
				params.put(VariableHolder.PMail, email.getText().toString().trim());
				return params;
			}
		};
		RequestQueue rq=Volley.newRequestQueue(getApplication());
		rq.add(str);
	}*/
	void EnterUser() {
		StringRequest stringRequest = new StringRequest(Request.Method.POST, VariableHolder.GetIDURL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						String id="";
						//Toast.makeText(getApplication(), s, Toast.LENGTH_LONG).show();
						//ans.setText(s);
						//ans.setTextColor(Color.BLACK);
						try {
							JSONObject jsonobject = new JSONObject(s);
							id = jsonobject.getString("Account_No");
							//Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
						}catch (JSONException e){
							e.printStackTrace();
						}
						editor.putString(VariableHolder.SPId,id);
						editor.commit();
						//goToUserDetails();
//						showJSON(s);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(getApplication(), volleyError.toString(), Toast.LENGTH_LONG).show();
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> Params = new HashMap<>();
				Params.put(VariableHolder.PMail, email.getText().toString().trim());
				Params.put(VariableHolder.PName, username.getText().toString().trim());
				Params.put(VariableHolder.Ppass, pass.getText().toString().trim());
				Params.put(VariableHolder.PSQ1, SQ1.getText().toString().trim());
				Params.put(VariableHolder.PSQ1A, SQ1a.getText().toString().trim());
				Params.put(VariableHolder.PSQ2, SQ2.getText().toString().trim());
				Params.put(VariableHolder.PSQ2A, SQ2a.getText().toString().trim());
				return Params;
			}
		};
		RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
		rq.add(stringRequest);
	}
	private void goToUserDetails(){
		Intent intent = new Intent(this , CustomerDetails.class);
		startActivity(intent);
	}

	boolean verifyAllFields(){
		boolean flag=true;
		if((username.getText().toString().equals(null)))
			flag=false;
		if((email.getText().toString().equals(null)))
			flag=false;
		else
		if(!isValidEmail(email.getText().toString()))
			flag=false;
		if((pass.getText().toString().equals(null)))
			flag=false;
		if((SQ1a.getText().toString().equals(null)))
			flag=false;
		if((SQ2a.getText().toString().equals(null)))
			flag=false;
		return flag;
	}
		/*submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (checkFlag()) {
					try {
						HttpClient httpClient = new DefaultHttpClient();
						httpClient.getParams().setParameter(
								CoreProtocolPNames.USER_AGENT,
								System.getProperty("http.agent"));
						HttpResponse httpResponse = null;
						HttpEntity httpEntity = null;
						String response = "";
						HttpPost httpPost = new HttpPost(
								"http://www.mazelon.com/preexam/preexam/k2reg.php?email="+email.getText().toString());
						httpResponse = httpClient.execute(httpPost);
						httpEntity = httpResponse.getEntity();
						response = EntityUtils.toString(httpEntity);
						response = response.substring(1, response.length() - 1);

						if (response.toLowerCase().equals("success")) {
														
							//SharedPreferences settings = getSharedPreferences(
							//		"login", 0);
							//SharedPreferences.Editor editor = settings.edit();
							//editor.putString("name", e);
							//editor.putString("username", uname.getText().toString());
							//editor.putString("street", street.getText()
							//		.toString());
							//editor.putString("city", city.getText().toString());
							//editor.putString("state", state.getText()
							//		.toString());
							//editor.putString("pincode", pin.getText()
							//		.toString());
							//editor.putBoolean("logged", true);
							//editor.commit();
							Global.userName = username.getText().toString();
							Global.password = pass.getText().toString();
							Global.email = email.getText().toString();
							Intent i = new Intent(getApplicationContext(),
									Details.class);
							startActivity(i);
							finish();							

						} else if (response.toLowerCase().equals("failure")) {
							Toast.makeText(getApplicationContext(),
									"Email id already exists!",
									Toast.LENGTH_LONG).show();
							email.setText("");
						} else {
							Toast.makeText(getApplicationContext(),
									"Internet Connection Error!",
									Toast.LENGTH_LONG).show();
						}
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getApplicationContext(),
								"Internet Connection Error!", Toast.LENGTH_LONG)
								.show();
					}					
				} else {
					AlertDialog.Builder b = new AlertDialog.Builder(
							MainActivity.this);
					b.setTitle("Signup Incomplete!");
					b.setPositiveButton("Ok",
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
		});*/


	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public void afterTextChanged(Editable edit) {
		// TODO Auto-generated method stub
		if (edit == username.getEditableText()) {
			if (username.getText().toString().length() < 2) {
				//username.setError("Invalid Username!");
				flag[0] = false;
			} else {
				flag[0] = true;
			}
		} else if (edit == email.getEditableText()) {
			if (!isValidEmail(email.getText().toString())) {
				//email.setError("Invalid Email!");
				flag[1] = false;
			} else {
				flag[1] = true;
			}
		}
	}


	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
								  int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}
	public boolean checkFlag() {
		boolean validated = true;
		for(int k=0;k<3;k++) {
			if(flag[k] == false) {
				validated = false;
				break;
			} else validated = true;
		}
		return validated;
	}
	boolean doubleBackToExitPressedOnce = false;

	@Override
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
	}
}
