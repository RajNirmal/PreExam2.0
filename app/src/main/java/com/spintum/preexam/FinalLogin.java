package com.spintum.preexam;

/**
 * Created by Nirmal on 8/21/2016.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FinalLogin extends Activity {
    String Name,Mail;
    EditText SQ1,SQ1A,SQ2,SQ2A;
    Button allSubmit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Name=savedInstanceState.getString(VariableHolder.SPName,"N");
        Mail=savedInstanceState.getString(VariableHolder.SPmail,"M");
        setContentView(R.layout.activity_secret_question);
        SQ1=(EditText)findViewById(R.id.SQ1);
        SQ1A=(EditText)findViewById(R.id.SQa1);
        SQ2=(EditText)findViewById(R.id.SQ2);
        SQ2A=(EditText)findViewById(R.id.SQa2);
        allSubmit=(Button)findViewById(R.id.submit_user_all_details);
        sharedPreferences = getApplication().getSharedPreferences(VariableHolder.UserSharedPrefName,MODE_PRIVATE);

        allSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = verifyAllFields();
                if (!check) {
                    AlertDialog alertDialog = new AlertDialog.Builder(FinalLogin.this).create();
                    // Setting Dialog Title
                    alertDialog.setTitle("Wrong Input");
                    // Setting Dialog Message
                    alertDialog.setMessage("Please enter all the fields");
                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) { }
                    });
                    // Showing Alert Message
                    alertDialog.show();
                } else {
                    editor = sharedPreferences.edit();
                    String SPUserName, SPUserMail, SPUserPass;
                    SPUserName = Name;
                    SPUserMail = Mail;
                    SPUserPass = Mail;
                    editor.putString(VariableHolder.SPName, SPUserName);
                    editor.putString(VariableHolder.SPmail, SPUserMail);
                    editor.putString(VariableHolder.SPpass, SPUserPass);
                    editor.commit();
                    EnterUser();
                    //getID();
                }

            }
        });
    }
    void EnterUser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, VariableHolder.GetIDURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String id="";
                        //Toast.makeText(getApplication(), s, Toast.LENGTH_LONG).show();
                      /*  ans.setText(s);
                        ans.setTextColor(Color.BLACK);*/
                        try {
                            JSONObject jsonobject = new JSONObject(s);
                            id = jsonobject.getString("Account_No");
                            Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        SharedPreferences sharedPreferences= getApplication().getSharedPreferences(VariableHolder.UserSharedPrefName,MODE_PRIVATE);
                        SharedPreferences.Editor edit= sharedPreferences.edit();
                        edit.putString(VariableHolder.SPId,id);
                        edit.commit();
//						showJSON(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplication(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> Params = new HashMap<>();
                Params.put(VariableHolder.PMail, Mail);
                Params.put(VariableHolder.PName, Name);
                Params.put(VariableHolder.Ppass, Mail);
                Params.put(VariableHolder.PSQ1, SQ1.getText().toString().trim());
                Params.put(VariableHolder.PSQ1A, SQ1A.getText().toString().trim());
                Params.put(VariableHolder.PSQ2, SQ2.getText().toString().trim());
                Params.put(VariableHolder.PSQ2A, SQ2A.getText().toString().trim());
                return Params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(stringRequest);
    }
    boolean verifyAllFields(){
        boolean flag=true;
        if((SQ1.getText().toString().equals(null)))
            flag=false;
        if((SQ2.getText().toString().equals(null)))
            flag=false;
        if((SQ1A.getText().toString().equals(null)))
            flag=false;
        if((SQ2A.getText().toString().equals(null)))
            flag=false;
        return flag;
    }
}
