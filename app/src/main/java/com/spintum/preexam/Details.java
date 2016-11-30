package com.spintum.preexam;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

public class Details extends FragmentActivity {
	Button submit;
	RadioButton male, female;
	EditText instn, country, pincode;
	ArrayList<String> countries = new ArrayList<String>();
	ListView v;
	boolean con = false, inst = false, pin = false, gender = false,flag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		Toast.makeText(getApplicationContext(),Global.email,Toast.LENGTH_SHORT).show();

		DataInputStream f = new DataInputStream(getResources().openRawResource(
				R.raw.countries));

		int i = 0;
		try {
			String temp;
			while ((temp = f.readLine()) != null) {
				countries.add(temp);
				i++;
			}
		} catch (Exception e) {
		}

		submit = (Button) findViewById(R.id.submit);
		country = (EditText) findViewById(R.id.res);
		//male = (RadioButton) findViewById(R.id.male);
		//female = (RadioButton) findViewById(R.id.female);
		instn = (EditText) findViewById(R.id.inst);
		pincode = (EditText) findViewById(R.id.pin);

		instn.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
									  int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
										  int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (arg0.toString().length() == 0) {
					instn.setError("Please Fill in this field!");
					inst = false;
				} else
					inst = true;
			}
		});
		pincode.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
									  int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
										  int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (arg0.toString().length() < 4
						&& arg0.toString().length() > 6) {
					pincode.setError("Invalid PinCode!");
					pin = false;
				} else
					pin = true;
			}
		});

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (con && inst && pin /*&& gender*/) {
					try {
						HttpClient httpClient = new DefaultHttpClient();
						httpClient.getParams().setParameter(
								CoreProtocolPNames.USER_AGENT,
								System.getProperty("http.agent"));
						HttpResponse httpResponse = null;
						HttpEntity httpEntity = null;
						String response = "";
						String post = "http://www.mazelon.com/preexam/preexam/k2regdet.php?email="
								+ Global.email + "&password="
								+ Global.password + "&pincode="
								+ pincode.getText().toString()
								+ "&resicoun="
								+ country.getText().toString()
								+ "&eduinsname="
								+ instn.getText().toString() + "&name="
								+ Global.userName;
						SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
						SharedPreferences.Editor edit = pref.edit();
						edit.putString("Name", Global.userName);
						edit.putString("Email", Global.email);
						edit.putString("Password", Global.password);
						edit.putString("Pincode",pincode.getText().toString());
						edit.putString("Country",country.getText().toString());
						edit.putString("Institute",instn.getText().toString());
						edit.commit();
						AlertDialog.Builder build = new AlertDialog.Builder(Details.this);
						build.setTitle("Sent URL is");
						build.setMessage(post);
						build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
							}
						});
						build.show();
						HttpPost httpPost = new HttpPost(post);
						/**"http://www.mazelon.com/preexam/preexam/k2regdet.php?email="
						 + Global.email + "&password="
						 + Global.password + "&pincode="
						 + pincode.getText().toString()
						 + "&resicoun="
						 + country.getText().toString()
						 + "&eduinsname="
						 + instn.getText().toString()+"&name="
						 + Global.userName);**/

						httpResponse = httpClient.execute(httpPost);
						httpEntity = httpResponse.getEntity();
						response = EntityUtils.toString(httpEntity);
						response = response.substring(1, response.length() - 1);
						if (response.toLowerCase().equals("success")) {
							flag = true;
							Intent i = new Intent(getApplicationContext(),
									Home.class);
							startActivity(i);
							finish();
						} else {
							AlertDialog.Builder b = new AlertDialog.Builder(
									Details.this);
							b.setTitle("Something went wrong try again later");
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

					} catch (Exception e) {
						if (flag == true) {
							Intent i = new Intent(getApplicationContext(),
									Home.class);
							startActivity(i);
							finish();

						}
						AlertDialog.Builder b = new AlertDialog.Builder(
								Details.this);
						b.setTitle("No Internet Connection!");
						b.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
														int arg1) {
										// TODO Auto-generated method stub

									}
								});
						b.show();

						Log.d("", e.toString());
					}
				} else {
					AlertDialog.Builder b = new AlertDialog.Builder(
							Details.this);
					b.setTitle("Please enter all fields");
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
		});
		country.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final Dialog d = new Dialog(Details.this);
				d.setContentView(R.layout.country_picker);
				d.setTitle("Choose Your Country");
				v = (ListView) d.findViewById(R.id.country_picker_listview);
				EditText search = (EditText) d
						.findViewById(R.id.country_picker_search);
				countries.add("India");
				final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
						Details.this, android.R.layout.simple_list_item_1,
						countries);
				v.setAdapter(arrayAdapter);
				v.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
											int index, long arg3) {
						// TODO Auto-generated method stub
						con = true;
						country.setText(arrayAdapter.getItem(index));
						d.dismiss();
					}
				});
				search.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence arg0, int arg1,
											  int arg2, int arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1,
												  int arg2, int arg3) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable arg0) {
						// TODO Auto-generated method stub
						arrayAdapter.getFilter().filter(arg0.toString());
					}
				});
				d.setCancelable(true);
				d.show();
			}
		});

	}

/*	public void gender(View v) {
		boolean checked = ((RadioButton) v).isChecked();
		if (v.getId() == R.id.male) {
			if (checked) {
				gender = true;
				male.setButtonDrawable(R.drawable.checked);
				female.setButtonDrawable(R.drawable.radiouncheck);
			} else {
				female.setButtonDrawable(R.drawable.checked);
				male.setButtonDrawable(R.drawable.radiouncheck);
			}
		}
		if (v.getId() == R.id.female) {
			if (checked) {
				gender = true;
				female.setButtonDrawable(R.drawable.checked);
				male.setButtonDrawable(R.drawable.radiouncheck);
			} else {
				female.setButtonDrawable(R.drawable.radiouncheck);
				male.setButtonDrawable(R.drawable.checked);
			}
		}
	}*/

}
