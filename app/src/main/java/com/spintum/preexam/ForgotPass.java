package com.spintum.preexam;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.os.StrictMode;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ForgotPass extends Activity {
    EditText email;
    TextView log;
    Button submit;
    String r[] = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        this.overridePendingTransition(R.layout.fade_in, R.layout.fade_out);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(pol);

        email = (EditText) findViewById(R.id.email);
        log = (TextView) findViewById(R.id.login);
        submit = (Button) findViewById(R.id.submit);

        log.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (isValidEmail(email.getText().toString())) {
                    // verify account and send mail
                    try {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpResponse httpResponse = null;
                        HttpEntity httpEntity = null;
                        String response = "";
                        HttpPost httpPost = new HttpPost(
                                "http://www.mazelon.com/preexam/preexam/k2fp.php?email="
                                        + email.getText().toString());

                        httpResponse = httpClient.execute(httpPost);

                        httpEntity = httpResponse.getEntity();
                        response = EntityUtils.toString(httpEntity);
                        response = response.substring(1, response.length() - 1);
                        r = response.split("\\|");
                        // Toast.makeText(getApplicationContext(),
                        // response.toString(), Toast.LENGTH_LONG).show();
                        if (r[0].equals("Success")) {

                            AlertDialog.Builder b = new AlertDialog.Builder(
                                    ForgotPass.this);
                            b.setTitle("Password Sent!");
                            b.setMessage("Your password has been sent to your email!Please check your mail and login again.");
                            b.setCancelable(false);
                            b.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface arg0, int arg1) {
                                            // TODO Auto-generated method stub
                                            Intent i = new Intent(
                                                    ForgotPass.this,
                                                    Login.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });
                            b.show();
                        } else {
                            email.setText("");

                            Toast.makeText(getApplicationContext(),
                                    "Email Id does not Exist!",
                                    Toast.LENGTH_LONG).show();
                            AlertDialog.Builder b = new AlertDialog.Builder(
                                    ForgotPass.this);
                            b.setTitle("UserName Invalid!");
                            b.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface arg0, int arg1) {
                                            // TODO Auto-generated method stub

                                        }
                                    });
                            b.show();
                        }
                    } catch (Exception e) {
                    }

                }
            }
        });

    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_forgot_pass, menu);
        return true;
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
