package com.spintum.preexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nirmal on 30/11/16.
 */

public class Exam_cards extends Activity implements View.OnClickListener{
    TabLayout tabs;
    TextView showText;
    View Opt1,Opt2,Opt3,Opt4;
    TextView Opt1Text, Opt2Text,Opt3Text,Opt4Text,CountDownText;
    Button Opt1Button,Opt2Button,Opt3Button,Opt4Button,GiveUp;
    String Questions[],Answer1[],Answer2[],Answer3[],Answer4[],Correct[];
    int Score[],Select[];
    private View swipeListingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_cards);
        tabs = (TabLayout)findViewById(R.id.exam_tablayout);
        showText = (TextView)findViewById(R.id.question_holder);
        Opt1Text = (TextView)findViewById(R.id.answer_1_text);
        Opt2Text = (TextView)findViewById(R.id.answer_2_text);
        Opt3Text = (TextView)findViewById(R.id.answer_3_text);
        Opt4Text = (TextView)findViewById(R.id.answer_4_text);
        CountDownText = (TextView)findViewById(R.id.count_down_text);
        Opt1Button = (Button)findViewById(R.id.answer_1_button);
        Opt2Button = (Button)findViewById(R.id.answer_2_button);
        Opt3Button = (Button)findViewById(R.id.answer_3_button);
        Opt4Button = (Button)findViewById(R.id.answer_4_button);
        GiveUp = (Button)findViewById(R.id.skip_button);
        Opt1 = findViewById(R.id.answer_1);
        Opt2 = findViewById(R.id.answer_2);
        Opt3 = findViewById(R.id.answer_3);
        Opt4 = findViewById(R.id.answer_4);
        Opt1.setOnClickListener(this);
        Opt2.setOnClickListener(this);
        Opt3.setOnClickListener(this);
        Opt4.setOnClickListener(this);
        Questions = new String[20];
        Answer1 = new String[20];
        Answer2 = new String[20];
        Answer3 = new String[20];
        Answer4 = new String[20];
        Correct = new String[20];
        Score = new int[20];
        Select = new int[20];
        Arrays.fill(Score,-1);
        Arrays.fill(Select,-1);
        writeTheData();
        for(int i=0; i<=19 ; i++)
            tabs.addTab(tabs.newTab().setText(String.valueOf(i+1)).setTag(String.valueOf(i)));
        tabs.setOnTabSelectedListener(tabSelectedListener);
        swipeListingView = findViewById(R.id.test_swiping_view);
        getQuestions();
        GiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ExamResult.class);
                startActivity(i);
            }
        });
        swipeListingView.setOnTouchListener(new OnSwipingListener(getApplicationContext())
            {
                @Override
                public void onSwipeLeft() {
                    super.onSwipeLeft();
                    int i = tabs.getSelectedTabPosition();
                    if(i!=0)
                        changeTab(i-1);
                }

                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();
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
            previouslySelected();
            updateTabColors();
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
        Opt1Text.setText(Answer1[x]);
        Opt2Text.setText(Answer2[x]);
        Opt3Text.setText(Answer3[x]);
        Opt4Text.setText(Answer4[x]);

    }
    private void deSelectAll(){
        Opt1Text.setTextColor(Color.parseColor("#000000"));
        Opt2Text.setTextColor(Color.parseColor("#000000"));
        Opt3Text.setTextColor(Color.parseColor("#000000"));
        Opt4Text.setTextColor(Color.parseColor("#000000"));
        Opt1Button.setBackgroundColor(Color.parseColor("#FF3D00"));
        Opt2Button.setBackgroundColor(Color.parseColor("#FF3D00"));
        Opt3Button.setBackgroundColor(Color.parseColor("#FF3D00"));
        Opt4Button.setBackgroundColor(Color.parseColor("#FF3D00"));

    }
    private void updateTabColors(){
        LinearLayout tabsContainer = (LinearLayout) tabs.getChildAt(0);
        for (int i = 0; i < tabs.getTabCount(); i++) {
            LinearLayout item = (LinearLayout) tabsContainer.getChildAt(i);
            TextView tv = (TextView) item.getChildAt(1);
            tv.setTextColor(Select[i] == -1 ? Color.BLACK : Color.BLUE);
        }
    }
    private void previouslySelected(){
        int i = tabs.getSelectedTabPosition();
       // i++;

        int x = Select[i];
        deSelectAll();
        switch (x){
            case 1:
                Opt1Button.setBackgroundColor(Color.parseColor("#0288D1"));
                Opt1Text.setTextColor(Color.parseColor("#0288D1"));
                break;
            case 2:
                Opt2Button.setBackgroundColor(Color.parseColor("#0288D1"));
                Opt2Text.setTextColor(Color.parseColor("#0288D1"));
                break;
            case 3:
                Opt3Button.setBackgroundColor(Color.parseColor("#0288D1"));
                Opt3Text.setTextColor(Color.parseColor("#0288D1"));
                break;
            case 4:
                Opt4Button.setBackgroundColor(Color.parseColor("#0288D1"));
                Opt4Text.setTextColor(Color.parseColor("#0288D1"));
                break;
            default:
                deSelectAll();
                break;

    }
    }
    private void selectButton(int x){
        switch (x){
            case 1:
                Opt1Button.setBackgroundColor(Color.parseColor("#0288D1"));
                Opt1Text.setTextColor(Color.parseColor("#0288D1"));
                break;
            case 2:
                Opt2Button.setBackgroundColor(Color.parseColor("#0288D1"));
                Opt2Text.setTextColor(Color.parseColor("#0288D1"));
                break;
            case 3:
                Opt3Button.setBackgroundColor(Color.parseColor("#0288D1"));
                Opt3Text.setTextColor(Color.parseColor("#0288D1"));
                break;
            case 4:
                Opt4Button.setBackgroundColor(Color.parseColor("#0288D1"));
                Opt4Text.setTextColor(Color.parseColor("#0288D1"));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        int i  = tabs.getSelectedTabPosition();
      //  i++;
        deSelectAll();
        if(view == Opt1){
            selectButton(1);
            Select[i] = 1;
            if(Correct[i].equals("A")) {
                Score[i] = 1;
             //   Toast.makeText(getApplicationContext(), "Clicked Correctly", Toast.LENGTH_SHORT).show();
            }
            else {
                Score[i] = 0;
              //  Toast.makeText(getApplicationContext(), "Clicked Wrongly", Toast.LENGTH_SHORT).show();
            }
        }
        else if (view == Opt2) {
            Select[i] = 2;
            selectButton(2);
            if (Correct[i].equals("B")) {
                Score[i] = 1;
               // Toast.makeText(getApplicationContext(), "Clicked Correctly", Toast.LENGTH_SHORT).show();
            } else {
                Score[i] = 0;
               // Toast.makeText(getApplicationContext(), "Clicked Wrongly", Toast.LENGTH_SHORT).show();
            }
        }
        else if (view == Opt3) {
            Select[i] = 3;
            selectButton(3);
            if (Correct[i].equals("C")) {
                Score[i] = 1;
               // Toast.makeText(getApplicationContext(), "Clicked Correctly", Toast.LENGTH_SHORT).show();
            } else {
                Score[i] = 0;
              //  Toast.makeText(getApplicationContext(), "Clicked Wrongly", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Select[i] = 4;
            selectButton(4);
            if (Correct[i].equals("D")) {
                Score[i] = 1;
              //  Toast.makeText(getApplicationContext(), "Clicked Correctly", Toast.LENGTH_SHORT).show();
            } else {
                Score[i] = 0;
               // Toast.makeText(getApplicationContext(), "Clicked Wrongly", Toast.LENGTH_SHORT).show();
            }
        }
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
        Opt1Text.setText(Answer1[0]);
        Opt2Text.setText(Answer2[0]);
        Opt3Text.setText(Answer3[0]);
        Opt4Text.setText(Answer4[0]);
    }
   private void writeTheData() {
       CountDownTimer CDT = new CountDownTimer(1200000, 1000) {
           @Override
           public void onTick(long l) {
               if (((l / 1000) % 60) < 10) {
                   CountDownText.setText("Time: "
                           + (l / 1000) / 60 + ":0"
                           + ((l / 1000) % 60));
               } else {
                   CountDownText.setText("Time: "
                           + (l / 1000) / 60 + ":"
                           + ((l / 1000) % 60));
               }


           }
           @Override
           public void onFinish() {
                Intent intent = new Intent(getApplicationContext(),ExamResult.class);
                startActivity(intent);
           }
       };
       CDT.start();
    }
}

