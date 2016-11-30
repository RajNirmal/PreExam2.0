package com.spintum.preexam;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomWithFriends extends ArrayAdapter<String> {

	private final Activity context;

	private final String[] profile,sc,t;
	int count = 1,ow,tw;
	TextView prof;
	TextView user;
	TextView score,time;

	public CustomWithFriends(Activity context, String[] profile,String[] score,String[] time,int owidth,int twidth) {
		super(context, R.layout.withfriends, profile);
		this.context = context;
		this.sc = score;
		this.profile = profile;
		this.t = time;
		ow = owidth;
		tw = twidth;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.withfriends, null, true);
		prof = (TextView) rowView.findViewById(R.id.profile);
		user = (TextView) rowView.findViewById(R.id.userPic);
		score = (TextView) rowView.findViewById(R.id.score);
		time = (TextView) rowView.findViewById(R.id.time);

		prof.setText(profile[position]);
		score.setText(sc[position]);
		time.setText(t[position]);

		score.setWidth(ow);
		time.setWidth(tw);
		//score.setTextColor(0xff0000ff);
		//activity_get_user_details.setBackground(context.getResources().getDrawable(image[position]));
		user.setBackground(context.getResources().getDrawable(R.drawable.usercircle));
		user.setText(prof.getText().toString().substring(0,1));
		return rowView;
	}
}
