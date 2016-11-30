package com.spintum.preexam;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import java.util.List;
/**
 * Created by Nirmal on 6/20/2016.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    private List<Fragment>fragments;
    public PagerAdapter(FragmentManager fm,List<Fragment> fragments){
        super(fm);
        this.fragments=fragments;
    }
    @Override
    public Fragment getItem(int position){
        return this.fragments.get(position);
    }
    @Override
    public int getCount(){
        return fragments.size();
    }
}
