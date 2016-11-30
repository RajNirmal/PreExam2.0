package com.spintum.preexam;

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.IntentSender;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;


public class Login extends Activity implements OnClickListener,
		GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
	private static final int RC_SIGN_IN = 0;
	// Logcat tag
	private static final String TAG = "Slider";

	// Profile pic image size in pixels
	private static final int PROFILE_PIC_SIZE = 400;

	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;

	/**
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;

	private boolean mSignInClicked;

	private ConnectionResult mConnectionResult;

	private SignInButton btnSignIn;
	/*private Button btnSignOut, btnRevokeAccess;
        private ImageView imgProfilePic;
        private TextView txtName, txtEmail;
        private LinearLayout llProfileLayout;*/
	private CallbackManager callbackManager;
	LoginDataBaseAdapter loginDataBaseAdapter1;
	private static EditText emailid, password;
	private static Button loginButton;
	private static TextView forgotPassword, signUp;
	private static CheckBox show_hide_password;
	private static LinearLayout loginLayout;
	private static Animation shakeAnimation;

	private TextView btnLogin,btnSign;
	private ProgressDialog progressDialog;
	User user;

	LoginDataBaseAdapter loginDataBaseAdapter;





	boolean completed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		initViews();
		setListeners();
		if (PrefUtils.getCurrentUser(Login.this) != null) {

			Intent homeIntent = new Intent(Login.this, Greeting.class);

			startActivity(homeIntent);

			finish();
		}
		StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(pol);
		previouslyLog();
		this.overridePendingTransition(R.layout.fade_in, R.layout.fade_out);

		View decorView = getWindow().getDecorView();
// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.=
		btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);


		btnSign = (TextView) findViewById(R.id.btnSign);
		btnSign.setOnClickListener(this);
		mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Plus.API)
				.addConnectionCallbacks(this).addOnConnectionFailedListener(this)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();


	}


	private void setListeners() {
		loginButton.setOnClickListener(this);

		signUp.setOnClickListener(this);
		// Set check listener over checkbox for showing and hiding password
		show_hide_password
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton button,
												 boolean isChecked) {

						// If it is checkec then show password else hide
						// password
						if (isChecked) {

							show_hide_password.setText(R.string.hide_pwd);// change
							// checkbox
							// text

							password.setInputType(InputType.TYPE_CLASS_TEXT);
							password.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());// show password
						} else {
							show_hide_password.setText(R.string.show_pwd);// change
							// checkbox
							// text

							password.setInputType(InputType.TYPE_CLASS_TEXT
									| InputType.TYPE_TEXT_VARIATION_PASSWORD);
							password.setTransformationMethod(PasswordTransformationMethod
									.getInstance());// hide password

						}

					}
				});
	}

	private void initViews() {
		emailid = (EditText) findViewById(R.id.login_emailid);
		password = (EditText) findViewById(R.id.login_password);
		loginButton = (Button) findViewById(R.id.loginBtn);
		forgotPassword = (TextView) findViewById(R.id.forgot_password);
		signUp = (TextView) findViewById(R.id.createAccount);

		loginDataBaseAdapter1 = new LoginDataBaseAdapter(getApplicationContext());
		loginDataBaseAdapter1.open();

		forgotPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(Login.this);
				dialog.getWindow();
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.forgot_search);
				dialog.show();

				final EditText security = (EditText) dialog.findViewById(R.id.securityhint_edt);
				final TextView getpass = (TextView) dialog.findViewById(R.id.textView3);

				Button ok = (Button) dialog.findViewById(R.id.getpassword_btn);
				Button cancel = (Button) dialog.findViewById(R.id.cancel_btn);

				ok.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {

						String userName = security.getText().toString();
						if (userName.equals("")) {
							Toast.makeText(getApplicationContext(), "Please enter your securityhint", Toast.LENGTH_SHORT).show();
						} else {
							String storedPassword = loginDataBaseAdapter1.getAllTags(userName);
							if (storedPassword == null) {
								Toast.makeText(getApplicationContext(), "Please enter correct securityhint", Toast.LENGTH_SHORT).show();
							} else {
								Log.d("GET PASSWORD", storedPassword);
								getpass.setText(storedPassword);
							}
						}
					}
				});
				cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

				dialog.show();

			}
		});
		show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
		loginLayout = (LinearLayout) findViewById(R.id.login_layout);

		// Load ShakeAnimation
		shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

		// Setting text selector over textviews
		XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			forgotPassword.setTextColor(csl);
			show_hide_password.setTextColor(csl);
			signUp.setTextColor(csl);
		} catch (Exception e) {
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.loginBtn:
				checkValidation();
				break;

			case R.id.btn_sign_in:
				// Signin button clicked
				signInWithGplus();
				break;
			case R.id.createAccount:

				// Replace signup frgament with animation
				Intent sign = new Intent(Login.this, SignupActivity.class);
				startActivity(sign);
				break;
		}
	}

	private void checkValidation() {
		// Get email id and password
		String getEmailId = emailid.getText().toString();
		String getPassword = password.getText().toString();

		String storedPassword = loginDataBaseAdapter1.getSinlgeEntry(getEmailId);


		// Check patter for email id
		Pattern p = Pattern.compile(Utils.regEx);

		Matcher m = p.matcher(getEmailId);

		// Check for both field is empty or not
		if (getEmailId.equals("") || getEmailId.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0) {
			loginLayout.startAnimation(shakeAnimation);

			Toast.makeText(Login.this, "Enter both credentials.", Toast.LENGTH_LONG).show();

		} else if (getPassword.equals(storedPassword)) {
			Toast.makeText(Login.this, "Congrats: Login Successfully", Toast.LENGTH_LONG).show();
			Intent in=new Intent(Login.this,Greeting.class);
			startActivity(in);

		}
		// Check if email id is valid or not
		else {
			Toast.makeText(Login.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();


		}
	}

	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (IntentSender.SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The activity_get_user_details has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the activity_get_user_details is signed in, or they cancel.
				resolveSignInError();
			}
		}

	}

	@Override
	protected void onResume() {
		super.onResume();


		callbackManager=CallbackManager.Factory.create();

		loginButton= (LoginButton)findViewById(R.id.login_button);

		//loginButton.setReadPermissions("public_profile", "email","user_friends");

		btnLogin= (TextView) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				progressDialog = new ProgressDialog(Login.this);
				progressDialog.setMessage("Loading...");
				progressDialog.show();

				loginButton.performClick();

				loginButton.setPressed(true);

				loginButton.invalidate();

				//loginButton.registerCallback(callbackManager, mCallBack);

				loginButton.setPressed(false);

				loginButton.invalidate();

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RC_SIGN_IN) {
			if (resultCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
		else {
			callbackManager.onActivityResult(requestCode, resultCode, data);
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		mSignInClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

		// Get activity_get_user_details's information
		getProfileInformation();

		// Update the UI after signin
		updateUI(true);

	}

	/**
	 * Updating the UI, showing/hiding buttons and profile layout
	 * */
	private void updateUI(boolean isSignedIn) {
		if (isSignedIn) {
			btnSignIn.setVisibility(View.GONE);
			//  btnSignOut.setVisibility(View.VISIBLE);
			//btnRevokeAccess.setVisibility(View.VISIBLE);
			//llProfileLayout.setVisibility(View.VISIBLE);
		} else {
			btnSignIn.setVisibility(View.VISIBLE);
			// btnSignOut.setVisibility(View.GONE);
			// btnRevokeAccess.setVisibility(View.GONE);
			// llProfileLayout.setVisibility(View.GONE);
		}
	}
	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
				Log.e(TAG, "Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);
				Toast.makeText(Login.this, "Name:" + personName + "email:" + email, Toast.LENGTH_LONG).show();
				Global.email=email;
				Global.userName=personName;
				Global.password=email;
				Intent intent = new Intent(Login.this, Details.class);
				startActivity(intent);
				finish();

			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
		updateUI(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Button on click listener
	 * */



	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();

		}
	}

	/**
	 * Sign-out from google
	 * */
	private void signOutFromGplus() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();
			updateUI(false);
		}
	}

	/**
	 * Revoking access from google
	 * */
	private void revokeGplusAccess() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
					.setResultCallback(new ResultCallback<Status>() {
						@Override
						public void onResult(Status arg0) {
							Log.e(TAG, "User access revoked!");
							mGoogleApiClient.connect();
							updateUI(false);
						}

					});
		}
	}

	/**
	 * Background Async task to load activity_get_user_details profile picture from url
	 * */
	private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public LoadProfileImage(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
	private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
		@Override
		public void onSuccess(LoginResult loginResult) {

			progressDialog.dismiss();

			// App code
			GraphRequest request = GraphRequest.newMeRequest(
					loginResult.getAccessToken(),
					new GraphRequest.GraphJSONObjectCallback() {
						@Override
						public void onCompleted(
								JSONObject object,
								GraphResponse response) {

							Log.e("response: ", response + "");
							try {
								user = new User();
								user.facebookID = object.getString("id").toString();
								user.email = object.getString("email").toString();
								user.name = object.getString("name").toString();
								user.gender = object.getString("gender").toString();
								PrefUtils.setCurrentUser(user,Login.this);

							}catch (Exception e) {
								e.printStackTrace();
							}
							Global.userName=user.name;
							Global.email=user.email;
							Global.password=user.email;
							Toast.makeText(Login.this, "fb_id" + user.facebookID + "welcome " + user.name + "email" + user.email + "gender" + user.gender, Toast.LENGTH_LONG).show();
							Intent intent = new Intent(Login.this, Details.class);
							startActivity(intent);
							finish();
                           /* HttpClient httpclient = new DefaultHttpClient();
                            HttpEntity httpEntity = null;
                            String respo = "";
                            HttpPost httppost = new HttpPost("http://www.mazelon.com/preexam/preexam/k2fb.php");
                            try {
                                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                                nameValuePairs.add(new BasicNameValuePair("&id", activity_get_user_details.facebookID));
                                nameValuePairs.add(new BasicNameValuePair("&name", activity_get_user_details.name));
                                nameValuePairs.add(new BasicNameValuePair("&email", activity_get_user_details.email));
                                nameValuePairs.add(new BasicNameValuePair("&gender", activity_get_user_details.gender));

                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                                // Execute HTTP Post Request
                                HttpResponse resp = httpclient.execute(httppost);
                                httpEntity = resp.getEntity();
                                respo= EntityUtils.toString(httpEntity);
                                if (respo.toLowerCase().equals("success")) {
                                    Toast.makeText(Slider.this, "fb_id" + activity_get_user_details.facebookID + "welcome " + activity_get_user_details.name + "email" + activity_get_user_details.email + "gender" + activity_get_user_details.gender, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Slider.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    AlertDialog.Builder b = new AlertDialog.Builder(
                                            Slider.this);
                                    b.setTitle("Sorry!No Internet Connection!");
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
                            }catch (Exception e)
                            {

                            }
                            */
						}

					});

			Bundle parameters = new Bundle();
			parameters.putString("fields", "id,name,email,gender, birthday");
			request.setParameters(parameters);
			request.executeAsync();
		}

		@Override
		public void onCancel() {
			progressDialog.dismiss();
		}

		@Override
		public void onError(FacebookException e) {
			progressDialog.dismiss();
		}
	};

	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	boolean loggedIn() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = null;
			HttpEntity httpEntity = null;
			String response = "";
			HttpPost httpPost = new HttpPost(
					"http://www.mazelon.com/preexam/preexam/k2login.php?email="
							+ emailid.getText().toString() + "&password="
							+ password.getText().toString());

			httpResponse = httpClient.execute(httpPost);

			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);
			response = response.substring(1, response.length() - 1);
			// String[] r = response.split("\\|");
			if (response.toLowerCase().equals("success")) {
				// Global.isLoggedIn = true;
				// Global.activity_get_user_details = n;
				// Global.userName = r[0];
				// Global.street = r[1];
				// Global.city = r[2];
				// Global.state = r[3];
				// Global.pincode = r[4];

				SharedPreferences settings = getSharedPreferences("ExamLogin",
						0);
				SharedPreferences.Editor editor = settings.edit();
				// editor.putString("name", n);
				// editor.putString("username", Global.userName);
				// editor.putString("street", Global.street);
				// editor.putString("city", Global.city);
				// editor.putString("state", Global.state);
				// editor.putString("pincode", Global.pincode);

				// editor.putBoolean("logged", true);
				// editor.commit();
				return true;

			} else {
				emailid.setText("");
				password.setText("");
				AlertDialog.Builder b = new AlertDialog.Builder(
						Login.this);
				b.setTitle("Invalid Username/Password !");
				b.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(
									DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub

							}
						});
				b.show();

				Toast.makeText(getApplicationContext(), "Login Failed!",
						Toast.LENGTH_LONG).show();
				return false;
			}
		}catch(UnknownHostException e) {
			Log.d("",e.toString());
			AlertDialog.Builder b = new AlertDialog.Builder(
					Login.this);
			b.setTitle("Sorry you are not connected to the internet !");
			b.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(
								DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub

						}
					});
			b.show();
			return false;
		}
		catch (Exception e) {
			Log.d("", e.toString());
			return false;
		}
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
	public void previouslyLog() {
		SharedPreferences sp = getSharedPreferences(VariableHolder.UserSharedPrefName, MODE_PRIVATE);
		String name = "", mail = "";
		try {
			name = sp.getString(VariableHolder.SPName, "");
			mail = sp.getString(VariableHolder.SPmail, "");
		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(), "Please Sign In", Toast.LENGTH_SHORT).show();
		}
		if (!name.isEmpty()) {
			if (!mail.isEmpty()) {
				Intent i = new Intent(Login.this, Home.class);
				startActivity(i);
			}
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Close The Database
		loginDataBaseAdapter1.close();
	}
}
