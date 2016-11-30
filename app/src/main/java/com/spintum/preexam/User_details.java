package com.spintum.preexam;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.Random;


/**
 * Created by Nirmal on 10/2/2015.
 */
public class User_details extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button changefrag;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_loader);
        changefrag=(Button)findViewById(R.id.but_skip);
      //  randomgen();randomgen();randomgen();
   //loadFragment1();
       //randomgen();
        changefrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                         changeactivity();}});
}
   /* void loadFragment1(){
       // Toast.makeText(getApplicationContext(),"Pressed",Toast.LENGTH_LONG).show();
        Fragment fragment5=new prefs_fragment();
        FragmentTransaction fragmentTransaction5 =
                getFragmentManager().beginTransaction();
        fragmentTransaction5.replace(R.id.fragment_place, fragment5);
        fragmentTransaction5.commit();

    }*/
    public void changeactivity(){
        //Toast.makeText(getApplicationContext(),"Call from Main",Toast.LENGTH_SHORT).show();
        Intent frag=new Intent(User_details.this,User_page.class);
        startActivity(frag);
    }
  /*  void randomgen(){
        Random generator=new Random();
        int choose=1+generator.nextInt(6);
        //Toast.makeText(getApplicationContext(),Integer.toString(choose),Toast.LENGTH_SHORT).show();
        switch (choose){
            case 1:
                Fragment fragment=new mail_fragment();
                FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment);
                fragmentTransaction.commit();
                break;
            case 2:
                Fragment fragment1=new date_fragment();
                FragmentTransaction fragmentTransaction1 =
                        getFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.fragment_place, fragment1);
                fragmentTransaction1.commit();
                break;
            case 3:
                Fragment fragment2=new mobile_fragment();
                FragmentTransaction fragmentTransaction2 =
                        getFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_place, fragment2);
                fragmentTransaction2.commit();
                break;
            case 4:
                Fragment fragment3=new location_fragment();
                FragmentTransaction fragmentTransaction3 =
                        getFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.fragment_place, fragment3);
                fragmentTransaction3.commit();
                break;
            case 5:
                Fragment fragment4=new prefs_fragment();
                FragmentTransaction fragmentTransaction4 =
                        getFragmentManager().beginTransaction();
                fragmentTransaction4.replace(R.id.fragment_place, fragment4);
                fragmentTransaction4.commit();
                break;
            case 6:
                Fragment fragment5=new achieve_fragment();
                FragmentTransaction fragmentTransaction5 =
                        getFragmentManager().beginTransaction();
                fragmentTransaction5.replace(R.id.fragment_place, fragment5);
                fragmentTransaction5.commit();
         //       Toast.makeText(getApplicationContext(),Integer.toString(choose),Toast.LENGTH_SHORT).show();
               // break;
            default:
              //  changeactivity();
        }
    }*/
}