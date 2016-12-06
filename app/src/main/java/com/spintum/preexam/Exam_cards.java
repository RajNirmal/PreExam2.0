package com.spintum.preexam;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

/**
 * Created by nirmal on 30/11/16.
 */

public class Exam_cards extends Activity{
    TabLayout tabs;
    TextView showText;
    private View swipeListingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_cards);
        tabs = (TabLayout)findViewById(R.id.exam_tablayout);
        showText = (TextView)findViewById(R.id.question_holder);
        for(int i=0; i<=19 ; i++)
            tabs.addTab(tabs.newTab().setText(String.valueOf(i+1)).setTag(String.valueOf(i)));
        //tabs.addTab(tabs.newTab().setText("Tab2").setTag("2"));
        //tabs.addTab(tabs.newTab().setText("Tab3").setTag("3"));
        tabs.setOnTabSelectedListener(tabSelectedListener);
        swipeListingView = findViewById(R.id.test_swiping_view);
        swipeListingView.setOnTouchListener(new OnSwipingListener(getApplicationContext())
            {
                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    Toast.makeText(getApplicationContext(),"Swiped Left",Toast.LENGTH_SHORT).show();
                    int i = tabs.getSelectedTabPosition();
                    if(i!=0)
                        changeTab(i-1);
                }

                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();
                    Toast.makeText(getApplicationContext(),"Swiped Right",Toast.LENGTH_SHORT).show();
                    int i = tabs.getSelectedTabPosition();
                    if(i!=20)
                        changeTab(i+1);
                }
            });

    }
    protected void changeTab(int x){
        tabs.getTabAt(x).select();
    }
    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            String i = tab.getTag().toString();
            setContentofText(i);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
    void setContentofText(String s){
        int x = Integer.valueOf(s);
        showText.setText(s);
    }
    void getQuestions(){
        StringRequest sq = new StringRequest(Request.Method.POST, VariableHolder.EchoTestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Do Something with the data
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue r = Volley.newRequestQueue(getApplicationContext());
        r.add(sq);

    }
}
