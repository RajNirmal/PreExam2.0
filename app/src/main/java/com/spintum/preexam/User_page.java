package com.spintum.preexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Nirmal on 10/3/2015.
 */

public class User_page extends AppCompatActivity {
    Toolbar iclist;
    TextView name,mail,in,noconn,name1,mail1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    exec();
    //}
      //  void exec(){
       setContentView(R.layout.main_page);
        iclist = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(iclist);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(null);
        name=(TextView)findViewById(R.id.uname);
        mail=(TextView)findViewById(R.id.mail);
        in=(TextView)findViewById(R.id.textView10);
        noconn=(TextView)findViewById(R.id.noconn);
        name1=(TextView)findViewById(R.id.textView9);
        mail1=(TextView)findViewById(R.id.textView10);
        noconn.setVisibility(View.INVISIBLE);
        try{
            ParseUser user= ParseUser.getCurrentUser();
            name.setText(user.getUsername());
            mail.setText(user.getEmail());
        }
        catch (NullPointerException r){
            in.setVisibility(View.INVISIBLE);
        }
        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        if((ParseUser.getCurrentUser())!=null)
  //      {final String ussname=ParseUser.getCurrentUser().getUsername();
        query.whereEqualTo("username","" );
        query.findInBackground(new FindCallback<ParseUser>() {
        public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    for(ParseUser singleobjects:objects)
                    { name.setText(singleobjects.get("username").toString());
                      mail.setText(singleobjects.get("email").toString());}
                }
            else{
                    name.setVisibility(View.INVISIBLE);
                    mail.setVisibility(View.INVISIBLE);
                    name1.setVisibility(View.INVISIBLE);
                    mail1.setVisibility(View.INVISIBLE);
                    noconn.setText("No Connection Try again later");
                    noconn.setVisibility(View.VISIBLE);
                }

    }});}

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_login_, menu);
        getMenuInflater().inflate(R.menu.menu_login_,menu);
        return true;
    }

 /*   public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.UserDetails:
            {
               // Toast.makeText(getApplicationContext(),"Icon Clicked",Toast.LENGTH_SHORT).show();
                Intent frag=new Intent(this,change_details.class);
                startActivity(frag);
                return true;
            }
            case R.id.SearchUser:
            {
                // Toast.makeText(getApplicationContext(),"Icon Clicked",Toast.LENGTH_SHORT).show();
                Intent frag=new Intent(this,search_players_main_activity.class);
                startActivity(frag);
            return true;}
            case R.id.SeeUser:
            {
                // Toast.makeText(getApplicationContext(),"Icon Clicked",Toast.LENGTH_SHORT).show();
                Intent frag=new Intent(this,MapActivity.class);
                startActivity(frag);
                return true;}

            default:
               return super.onOptionsItemSelected(item);
        }
    }*/
    @Override
    protected void onStart() {
        super.onStart();  // Always call the superclass method first
       // exec();
        // The activity is either being restarted or started for the first time
        // so this is where we should make sure that GPS is enabled
            // Create a dialog here that requests the activity_get_user_details to enable GPS, and use an intent
            // with the android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS action
            // to take the activity_get_user_details to the Settings screen to enable GPS when they click "OK"
        }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
       // exec();
        // Activity being restarted from stopped state
    }
}