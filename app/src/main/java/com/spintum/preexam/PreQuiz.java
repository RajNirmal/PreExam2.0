package com.spintum.preexam;
import android.app.Fragment;


import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class PreQuiz extends Fragment {
	Button b1,b2,b3,b4,b5;
	Toolbar mToolbar;
	@Override
	public View onCreateView(LayoutInflater layoutInflater,ViewGroup container,Bundle SavedInstances){
		super.onCreate(SavedInstances);
		View subView=getActivity().getLayoutInflater().inflate(R.layout.activity_pre_quiz,container,false);
		//this.getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.background));
		//this.overridePendingTransition(R.layout.fade_in, R.layout.fade_out);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		b1=(Button)subView.findViewById(R.id.button1);
		//friends=(Button)findViewById(R.id.friends);
		b2=(Button)subView.findViewById(R.id.button2);
		b3=(Button)subView.findViewById(R.id.button3);
		b4=(Button)subView.findViewById(R.id.button4);
		b5=(Button)subView.findViewById(R.id.button5);

		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(),Instructions.class);
				startActivity(i);
			}
		});
		/*friends.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(),WithFriends.class);
				startActivity(i);
			}
		});*/
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(),Instructions1.class);
				startActivity(i);
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(),Instructions2.class);
				startActivity(i);
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(),Instructions3.class);
				startActivity(i);
			}
		});
		b5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getActivity(),Instructions4.class);
				startActivity(i);
			}
		});
		return subView;
	}

	/*boolean doubleBackToExitPressedOnce = false;

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
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}*/

}