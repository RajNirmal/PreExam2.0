package com.spintum.preexam;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SAIIVA on 2016-11-25.
 */
public class SignupActivity extends Activity {

    LoginDataBaseAdapter loginDataBaseAdapter1;
    private static EditText fullName, emailId, mobileNumber, location,
            password, confirmPassword,securityhint;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        fullName = (EditText) findViewById(R.id.fullName);
        emailId = (EditText)findViewById(R.id.userEmailId);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        location = (EditText)findViewById(R.id.location);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        securityhint=(EditText)findViewById(R.id.securityhint_edt);
        signUpButton = (Button) findViewById(R.id.signUpBtn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get all edittext texts
                loginDataBaseAdapter1 = new LoginDataBaseAdapter(getApplicationContext());
                loginDataBaseAdapter1=loginDataBaseAdapter1.open();
                String getFullName = fullName.getText().toString();
                String getEmailId = emailId.getText().toString();
                String getMobileNumber = mobileNumber.getText().toString();
                String getLocation = location.getText().toString();
                String getPassword = password.getText().toString();
                String getConfirmPassword = confirmPassword.getText().toString();
                String getSecurityhint = securityhint.getText().toString();

                // Pattern match for email id
                Pattern p = Pattern.compile(Utils.regEx);
                Matcher m = p.matcher(getEmailId);

                // Check if all strings are null or not
                if (getFullName.equals("") || getFullName.length() == 0
                        || getEmailId.equals("") || getEmailId.length() == 0
                        || getMobileNumber.equals("") || getMobileNumber.length() == 0
                        || getLocation.equals("") || getLocation.length() == 0
                        || getPassword.equals("") || getPassword.length() == 0
                        || getConfirmPassword.equals("")|| getConfirmPassword.length() == 0  || getSecurityhint.equals("") || getSecurityhint.length()==0)

                    Toast.makeText(SignupActivity.this,   "All fields are required.", Toast.LENGTH_LONG).show();
                    // Check if email id valid or not
                else if (!m.find())
                    Toast.makeText(SignupActivity.this,     "Your Email Id is Invalid.", Toast.LENGTH_LONG).show();
                    // Check if both password should be equal
                else if (!getConfirmPassword.equals(getPassword))
                    Toast.makeText(SignupActivity.this,      "Both password doesn't match.", Toast.LENGTH_LONG).show();
                    // Make sure user should check Terms and Conditions checkbox
                else if (!terms_conditions.isChecked())
                    Toast.makeText(SignupActivity.this,"Please select Terms and Conditions.", Toast.LENGTH_LONG).show();
                    // Else do signup_layout or do your stuff
                else
                    // Save the Data in Database
                    loginDataBaseAdapter1.insertEntry(getFullName,getPassword,getConfirmPassword,getEmailId,getLocation,getMobileNumber,getSecurityhint);
                 Toast.makeText(SignupActivity.this,"Do SignUp.", Toast.LENGTH_LONG).show();

            }
        });
        login = (TextView) findViewById(R.id.already_user);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(SignupActivity.this,Login.class);
                startActivity(login);
            }
        });
        terms_conditions = (CheckBox)findViewById(R.id.terms_conditions);

        // Setting text_selector selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }
    }
