package com.spintum.preexam;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import android.R.anim;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Country extends Fragment  {
	Button submit;
	ListView lv;
	EditText t;
	ArrayAdapter<String> adapter;
	ArrayList<String> countries = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.activity_country,
				container, false);

		//this.overridePendingTransition(R.layout.fade_in, R.layout.fade_out);
		getActivity().getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.background));
		try {
			
			DataInputStream f = new DataInputStream(getResources()
					.openRawResource(R.raw.countries));

			int i = 0;
			t = (EditText)root.findViewById(R.id.country);
			try {
				String temp;
				while ((temp = f.readLine()) != null) {
					countries.add(temp);
					i++;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				t.setText(e.toString());
			}

			submit = (Button)root.findViewById(R.id.submit);
			lv = (ListView) root.findViewById(R.id.countryList);

			adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_2,android.R.id.text2,countries);
			lv.setAdapter(adapter);
			t.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence cs, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					try {
						adapter.getFilter().filter(cs);
					}catch(NullPointerException e) {
						Log.e("",e.toString());
					}
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub

				}
			});
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					t.setText(adapter.getItem(arg2));
					//Toast.makeText(getApplicationContext(), adapter.getItem(arg2),Toast.LENGTH_LONG).show();
				}
			});

			submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(), Home.class);
					startActivity(i);
					
				}
			});

		} catch (Exception e) {
		}
		return root;
	}

}
