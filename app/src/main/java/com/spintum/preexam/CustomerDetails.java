package com.spintum.preexam;

/**
 * Created by Nirmal on 8/25/2016.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CustomerDetails extends Activity {
    EditText add1,add2,city,state,country,pin;
    String Add1,Add2,City,State,Country,Pin,Acc_No;
    TextView view1,log;
    Button submit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_customer_details);
        add1=(EditText)findViewById(R.id.ad1);
        add2=(EditText)findViewById(R.id.ad2);
        city=(EditText)findViewById(R.id.city);
        state=(EditText)findViewById(R.id.state);
        country=(EditText)findViewById(R.id.country);
        pin=(EditText)findViewById(R.id.pin);
        log = (TextView) findViewById(R.id.login);
        submit=(Button)findViewById(R.id.submit_customer_details);
        view1=(TextView)findViewById(R.id.testingSP);
        view1.setVisibility(View.GONE);
        sharedPreferences = getApplication().getSharedPreferences(VariableHolder.UserSharedPrefName,MODE_APPEND);
        log.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = verifyAllFields();

                if (!check) {
                    AlertDialog alertDialog = new AlertDialog.Builder(CustomerDetails.this).create();
                    // Setting Dialog Title
                    alertDialog.setTitle("Wrong Input");
                    // Setting Dialog Message
                    alertDialog.setMessage("Please enter all the fields");
                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            //        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                            //pass.setText("");
                        }
                    });
                    // Showing Alert Message
                    alertDialog.show();
                } else {

                    try{
                        Acc_No = sharedPreferences.getString(VariableHolder.SPId,"-1");
                        view1.setText(Acc_No);
                        view1.setTextColor(Color.BLACK);
                    }catch (NullPointerException e){
                        Toast.makeText(getApplicationContext(),"Please do not Clear the App data",Toast.LENGTH_SHORT).show();
                    }
                    Add1 = add1.getText().toString();
                    Add2 = add2.getText().toString();
                    City = city.getText().toString();
                    State = state.getText().toString();
                    Country = country.getText().toString();
                    Pin = pin.getText().toString();
                    editor = sharedPreferences.edit();
                    editor.putString(VariableHolder.SPAdd, Add1);
                    editor.putString(VariableHolder.SPAdd1,Add2 );
                    editor.putString(VariableHolder.SPCity, City);
                    editor.putString(VariableHolder.SPState, State);
                    editor.putString(VariableHolder.SPCountry, Country);
                    editor.putString(VariableHolder.SPPin, Pin);
                    editor.commit();
                    //Acc_No = sharedPreferences.getString(VariableHolder.SPId,"-1");
                   // Toast.makeText(getApplicationContext(),Acc_No,Toast.LENGTH_LONG).show();
                    //view1.setText(Acc_No);
                    EnterUserDetails();
                    Intent i = new Intent(CustomerDetails.this,Home.class);
                    startActivity(i);
                    //getID();
                }
            }
        });
    }
    void EnterUserDetails(){
        StringRequest str = new StringRequest( Request.Method.POST, VariableHolder.InsertUserDetailsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(getApplication(),s,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplication(),volleyError.toString(),Toast.LENGTH_SHORT);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(VariableHolder.SPId,Acc_No);
                params.put(VariableHolder.PAdd,Add1);
                params.put(VariableHolder.PAdd1,Add2);
                params.put(VariableHolder.PCity,City);
                params.put(VariableHolder.PState,State);
                params.put(VariableHolder.PCountry,Country);
                params.put(VariableHolder.PPin,Pin);
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getApplication());
        rq.add(str);
    }
    boolean verifyAllFields(){
        boolean flag=true;
        if((add1.getText().toString().equals("")))
            flag=false;
        if((state.getText().toString().equals("")))
            flag=false;
        if((country.getText().toString().equals("")))
            flag=false;
        if((pin.getText().toString().equals("")))
            flag=false;
        if((city.getText().toString().equals("")))
            flag=false;
        return flag;
    }
}
