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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by nirmal on 30/11/16.
 */

public class Exam_cards extends Activity{
    TabLayout tabs;
    TextView showText,Opt1,Opt2,Opt3,Opt4;
    String Questions[],Answer1[],Answer2[],Answer3[],Answer4[],Correct[];
    private View swipeListingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_cards);
        tabs = (TabLayout)findViewById(R.id.exam_tablayout);
        showText = (TextView)findViewById(R.id.question_holder);
        Opt1 = (TextView)findViewById(R.id.answer_1_text);
        Opt2 = (TextView)findViewById(R.id.answer_2_text);
        Opt3 = (TextView)findViewById(R.id.answer_3_text);
        Opt4 = (TextView)findViewById(R.id.answer_4_text);
        Questions = new String[20];
        Answer1 = new String[20];
        Answer2 = new String[20];
        Answer3 = new String[20];
        Answer4 = new String[20];
        Correct = new String[20];
        for(int i=0; i<=19 ; i++)
            tabs.addTab(tabs.newTab().setText(String.valueOf(i+1)).setTag(String.valueOf(i)));
        //tabs.addTab(tabs.newTab().setText("Tab2").setTag("2"));
        //tabs.addTab(tabs.newTab().setText("Tab3").setTag("3"));
        tabs.setOnTabSelectedListener(tabSelectedListener);
        swipeListingView = findViewById(R.id.test_swiping_view);
        getQuestions();
        swipeListingView.setOnTouchListener(new OnSwipingListener(getApplicationContext())
            {
                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                   // Toast.makeText(getApplicationContext(),"Swiped Left",Toast.LENGTH_SHORT).show();
                    int i = tabs.getSelectedTabPosition();
                    if(i!=0)
                        changeTab(i-1);
                }

                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();
                 //   Toast.makeText(getApplicationContext(),"Swiped Right",Toast.LENGTH_SHORT).show();
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
        showText.setText(Questions[x]);
        Opt1.setText(Answer1[x]);
        Opt2.setText(Answer2[x]);
        Opt3.setText(Answer3[x]);
        Opt4.setText(Answer4[x]);

    }
    void getQuestions(){
        StringRequest sq = new StringRequest(Request.Method.POST, VariableHolder.TestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Do Something with the data
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("json");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject questionSet = jsonArray.getJSONObject(i);
                                Questions[i] = questionSet.getString("Question");
                                Answer1[i] = questionSet.getString("Option1");
                                Answer2[i] = questionSet.getString("Option2");
                                Answer3[i] = questionSet.getString("Option3");
                                Answer4[i] = questionSet.getString("Option4");
                                Correct[i] = questionSet.getString("Correct");
                            }
                            setFirstTabsData();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                     //   showText.setText(response.toString());
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
    private void setFirstTabsData(){
        showText.setText(Questions[0]);
        Opt1.setText(Answer1[0]);
        Opt2.setText(Answer2[0]);
        Opt3.setText(Answer3[0]);
        Opt4.setText(Answer4[0]);
    }
   private void writeTheDamnData(){
        Toast.makeText(getApplicationContext(), "Writing the data now",Toast.LENGTH_SHORT).show();
        StringBuilder sb = new StringBuilder();
        sb.append(Questions[0]);
        sb.append("\n");
        sb.append(Answer1[0]);
       sb.append("\n");
        sb.append(Answer2[0]);
       sb.append("\n");
        sb.append(Answer3[0]);
       sb.append("\n");
        sb.append(Answer4[0]);
        showText.setText(sb.toString());

    }
}

