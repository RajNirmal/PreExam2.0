package com.spintum.preexam;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class CustomBySchool extends ArrayAdapter<String> {
	private final Activity context;	
	private final String[] name1,name2,name3;		
	RelativeLayout user1,user2,user3;
	TextView un1,un2,un3;
	int width;

	public CustomBySchool(Activity context, String[] name1,String[] name2, String[] name3,int width) {
		super(context, R.layout.byschool,name1);
		this.context = context;
		
		this.name1 = name1;
		this.name2 = name2;
		this.name3 = name3;
		this.width = width;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.byschool, null, true);		
		user1 = (RelativeLayout) rowView.findViewById(R.id.user1);
		user2 = (RelativeLayout) rowView.findViewById(R.id.user2);
		user3 = (RelativeLayout) rowView.findViewById(R.id.user3);
				
		un1 = (TextView) rowView.findViewById(R.id.username1);
		un2 = (TextView) rowView.findViewById(R.id.username2);
		un3 = (TextView) rowView.findViewById(R.id.username3);
		
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(width/3,width/3);
		//p.setMargins(2, 2, 2, 2);
		user1.setLayoutParams(p);
		user2.setLayoutParams(p);
		user3.setLayoutParams(p);
		
		RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(width/3,RelativeLayout.LayoutParams.WRAP_CONTENT);
		//p1.setMargins(2, 2, 2, 2);
		p1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		un1.setLayoutParams(p1);
		un2.setLayoutParams(p1);
		un3.setLayoutParams(p1);
		
		un1.setText(name1[position]);
		un2.setText(name2[position]);
		un3.setText(name3[position]);
		return rowView;
	}

}
