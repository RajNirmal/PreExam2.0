package com.spintum.preexam;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
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

import io.github.kexanie.library.MathView;

/**
 * Created by nirmal on 9/12/16.
 */

public class Testing_MathView extends Activity {
    String Questions[],Answer1[],Answer2[],Answer3[],Answer4[],Correct[];
    int ii;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ii = 0;
        final MathView maths = (MathView) findViewById(R.id.testingmaths);
        final TextView testingtrue = (TextView) findViewById(R.id.testingvalue);
        Button next = (Button)findViewById(R.id.gotonext);
        getQuestions();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String test = Questions[ii]+Answer1[ii]+Answer2[ii]+Answer3[ii]+Answer4[ii];
                maths.setText(test);
                testingtrue.setText(test);
                ii++;
            }
        });

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
                                Questions[i] = Questions[i].replace('$','\\');
                                Answer1[i] = questionSet.getString("Option1");
                                Answer1[i] = Answer1[i].replace('$','\\');
                                Answer2[i] = questionSet.getString("Option2");
                                Answer2[i] = Answer2[i].replace('$','\\');
                                Answer3[i] = questionSet.getString("Option3");
                                Answer3[i] = Answer3[i].replace('$','\\');
                                Answer4[i] = questionSet.getString("Option4");
                                Answer4[i] = Answer4[i].replace('$','\\');
                                Correct[i] = questionSet.getString("Correct");
                                Toast.makeText(getApplicationContext(),Questions[i]+Answer1[i],Toast.LENGTH_SHORT).show();
                            }
                            //setFirstTabsData();

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
}
