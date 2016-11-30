package com.spintum.preexam;

/**
 * Created by Nirmal on 6/28/2016.
 */
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;




public class Fragment_Statistics extends Fragment {
    DonutChart chart;
    TextView t1,t2;
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup container,Bundle SavedInstances){
        View statistics_fragment=getActivity().getLayoutInflater().inflate(R.layout.fragment_statistics, null);
        chart = (DonutChart) statistics_fragment.findViewById(R.id.donutChart);
        chart.getData(2,2);
        t1=(TextView)statistics_fragment.findViewById(R.id.PerformanceRating);
        t2=(TextView)statistics_fragment.findViewById(R.id.PerformanceRating_1);
        chart.setVisibility(View.INVISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                //buttons[inew][jnew].setBackgroundColor(Color.BLACK);
            }
        }, 2000);
        return statistics_fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Animation animation_clock=AnimationUtils.loadAnimation(getActivity(),R.anim.clockwise);
        chart.startAnimation(animation_clock);
        t1.startAnimation(animation_clock);
        t2.startAnimation(animation_clock);
    }

    @Override
    public void onResume() {
        super.onResume();
        chart.invalidate();
    }
    /*LinearLayout layout=(LinearLayout)statistics_fragment.findViewById(R.id.chart);
        CategorySeries category=new CategorySeries("Mark Weight");
        category.clear();
        category.add("RPM",50);
        DialRenderer renderer = new DialRenderer();
        renderer.setChartTitleTextSize(14);
        renderer.setLabelsTextSize(16);
        renderer.setLegendTextSize(20);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.GREEN);
        renderer.addSeriesRenderer(r);
        renderer.setVisualTypes(new DialRenderer.Type[]{Type.NEEDLE});
        renderer.setMinValue(0);
        renderer.setMaxValue(100);
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.BLACK);
        renderer.setPanEnabled(false);
        mChartView = ChartFactory.getDialChartView(getActivity(), category, renderer);
        layout.addView(mChartView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));*/
}
