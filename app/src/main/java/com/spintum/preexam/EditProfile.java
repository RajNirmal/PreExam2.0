package com.spintum.preexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ratan on 7/29/2015.
 */
public class EditProfile extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.editprofile,null);
        View rootView = inflater.inflate(R.layout.editprofile, container, false);
        Intent nextScreen = new Intent(getActivity(), SignupActivity.class);
        startActivity(nextScreen);


        return rootView;
    }
}
