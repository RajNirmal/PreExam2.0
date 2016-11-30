package com.spintum.preexam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * Created by SAIIVA on 2016-11-28.
 */

public class Feedback extends Fragment {
  RatingBar ratingbar1;
    Button btnsubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feedback, container, false);

        ratingbar1 = (RatingBar) rootView.findViewById(R.id.ratingBar1);
        btnsubmit = (Button) rootView.findViewById(R.id.button1);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = String.valueOf(ratingbar1.getRating());
                Toast.makeText(getActivity(), rating, Toast.LENGTH_LONG).show();
            }
        });
return rootView;
    }
}






