package com.spintum.preexam;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ShowcaseView showcaseView;
    CardView profAdvice;
    Button takeTest;
    LinearLayout l;
    private TextPaint titlePaint,textPaint;
    private static int count=0;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private static int pos;
    List<DrawerItem> dataList = new ArrayList<DrawerItem>();
    View statistics,test,syllabus,share;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawer;
    private int[] cl=new int[]{R.drawable.dralight,R.drawable.dralight2,R.drawable.dralight3,R.drawable.dralight4};
    private int[] cl1=new int[]{R.drawable.dr1,R.drawable.dr2,R.drawable.dr3,R.drawable.dr4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_draw);
        statistics=findViewById(R.id.statistics_container);
        test=findViewById(R.id.test_container);
        syllabus=findViewById(R.id.syllabus_container);
        share=findViewById(R.id.share_container);
        statistics.setOnClickListener(mBottomDrawerListener);
        syllabus.setOnClickListener(mBottomDrawerListener);
        test.setOnClickListener(mBottomDrawerListener);
        share.setOnClickListener(mBottomDrawerListener);
        TextView text=(TextView)findViewById(R.id.statistics);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        selectItem();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void selectItem(){
        FragmentManager fragmentManager = getFragmentManager();
        ImageView img=(ImageView)findViewById(R.id.test_image);
        img.setImageResource(cl1[1]);
        TextView text=(TextView)findViewById(R.id.Tests);
        text.setTextColor(getResources().getColor(R.color.navigation_drawer_color));
        fragmentManager.beginTransaction().replace(R.id.content_frame, new PreQuiz()).commit();
          }
    public View.OnClickListener mBottomDrawerListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager=getFragmentManager();
            setBlackText(v);
            TextView image,text;
            ImageView img;

            switch (v.getId()){

                case R.id.statistics_container:
                    img=(ImageView)v.findViewById(R.id.statistics_image);
                    text=(TextView)v.findViewById(R.id.statistics);
                    img.setImageResource(cl1[0]);
                    text.setTextColor(getResources().getColor(R.color.navigation_drawer_color));
                    fragmentManager.beginTransaction().replace(R.id.content_frame,new Fragment_Statistics()).commit();
                    break;
                case R.id.syllabus_container:
                    img=(ImageView)v.findViewById(R.id.syllabus_image);
                    img.setImageResource(cl1[2]);
                    text=(TextView)v.findViewById(R.id.syllabus_btm);
                    text.setTextColor(getResources().getColor(R.color.navigation_drawer_color));
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new FoldMain()).commit();
                    break;
                case R.id.test_container:
                    img=(ImageView)v.findViewById(R.id.test_image);
                    img.setImageResource(cl1[1]);
                    text=(TextView)v.findViewById(R.id.Tests);
                    text.setTextColor(getResources().getColor(R.color.navigation_drawer_color));
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new PreQuiz()).commit();
                    break;
                case R.id.share_container:
                    img=(ImageView)v.findViewById(R.id.share_image);
                    img.setImageResource(cl1[3]);
                    text=(TextView)v.findViewById(R.id.share_btm);
                    text.setTextColor(getResources().getColor(R.color.navigation_drawer_color));
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new Analytics()).commit();
                    break;
            }

        }
    };

    public void takeToTest(){
        test.callOnClick();
    }

    public void setBlackText(View v){
        ImageView image[]=new ImageView[4];
        TextView t1,t2,t3,t4;
        image[0]=(ImageView)findViewById(R.id.statistics_image);
        t4=(TextView)findViewById(R.id.statistics);
        image[1]=(ImageView)findViewById(R.id.test_image);
        t1=(TextView)findViewById(R.id.Tests);
        image[2]=(ImageView)findViewById(R.id.syllabus_image);
        t2=(TextView)findViewById(R.id.syllabus_btm);
        image[3]=(ImageView)findViewById(R.id.share_image);
        t3=(TextView)findViewById(R.id.share_btm);
        image[0].setImageResource(cl[0]);
        image[1].setImageResource(cl[1]);
        image[2].setImageResource(cl[2]);
        image[3].setImageResource(cl[3]);
        t1.setTextColor(Color.parseColor("#B3E5FC"));
        t2.setTextColor(Color.parseColor("#B3E5FC"));
        t3.setTextColor(Color.parseColor("#B3E5FC"));
        t4.setTextColor(Color.parseColor("#B3E5FC"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }

}
