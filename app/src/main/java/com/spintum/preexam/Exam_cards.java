package com.spintum.preexam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by nirmal on 30/11/16.
 */

public class Exam_cards extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_cards);



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
