package com.spintum.preexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


/**
 * Created by admin on 10/1/2016.
 */
public class Greeting extends Activity {
    CardView profAdvice;
    Button takeTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardhome);
        profAdvice=(CardView)findViewById(R.id.card_view);
        takeTest=(Button)findViewById(R.id.take_a_test);
        Animation animationk= AnimationUtils.loadAnimation(Greeting.this, R.anim.slide_up);
        profAdvice.startAnimation(animationk);
        takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in=new Intent(Greeting.this,Main2Activity.class);
                startActivity(in);

            }
        });
    }

}
