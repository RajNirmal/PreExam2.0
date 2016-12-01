package com.spintum.preexam;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_cards);
        tabs = (TabLayout)findViewById(R.id.exam_tablayout);
        showText = (TextView)findViewById(R.id.text_test);
        tabs.addTab(tabs.newTab().setText("Tab1").setTag("1"));
        tabs.addTab(tabs.newTab().setText("Tab2").setTag("2"));
        tabs.addTab(tabs.newTab().setText("Tab3").setTag("3"));
        tabs.setOnTabSelectedListener(tabSelectedListener);


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
