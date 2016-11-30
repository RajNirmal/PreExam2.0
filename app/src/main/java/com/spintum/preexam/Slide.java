package com.spintum.preexam;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.viewpagerindicator.CirclePageIndicator;

public class Slide extends AppCompatActivity {

    ViewPager mViewPager;
    SamplePagerAdapter mSectionsPagerAdapter;
    int c=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.slide_main);
        mViewPager = (ViewPager) findViewById(R.id.pager);
/** set the adapter for ViewPager */
        mSectionsPagerAdapter = new SamplePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
        indicator.setCurrentItem(0);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(3* density);
        indicator.setPageColor(0xFFFFFFFF);
        indicator.setFillColor(0xFF000000);
        indicator.setStrokeColor(0xFF000000);
        indicator.setStrokeWidth(1 * density);

    }

    /** Defining a FragmentPagerAdapter class for controlling the fragments to be shown when user swipes on the screen. */
    public class SamplePagerAdapter extends FragmentPagerAdapter {

        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /** Show a Fragment based on the position of the current screen */
            if (position == 0) {
                return new SampleFragment();
            } else if (position == 1) {
                return new SampleFragmentTwo();
            } else if (position == 2) {
                return new SampleFragmentThree();
            } else {
                return new SampleFragmentFour();
            }

        }
        @Override
        public int getCount() {
            // Show 2 total pages.
            return c;
        }


    }

}