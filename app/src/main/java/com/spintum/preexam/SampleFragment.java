package com.spintum.preexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SampleFragment extends Fragment {
    Button btn;
    TextView sign;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_one, container,
                false);
        btn=(Button)rootView.findViewById(R.id.btn);
        sign=(TextView)rootView.findViewById(R.id.sign);
       btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(), Login.class);
                startActivity(i);
            }
        });
        return rootView;
    }
}