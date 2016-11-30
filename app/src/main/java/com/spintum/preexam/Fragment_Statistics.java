package com.spintum.preexam;

/**
 * Created by Nirmal on 6/28/2016.
 */
        import android.app.Fragment;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.v7.widget.CardView;
        import android.view.ViewGroup;
        import android.view.View;
        import android.view.LayoutInflater;
        import android.widget.GridLayout;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;

        import az.plainpie.PieView;
        import java.util.Random;


public class Fragment_Statistics extends Fragment {
    String previous;
    int average;
    TextView rescard,maintext;
    ImageView imageView;
    CardView profAdvice,changeSubject1,changeSubject2,changeSubject3,changeSubject4,changeSubject5;
    int i;
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup container,Bundle SavedInstances){
        View statistics_fragment=getActivity().getLayoutInflater().inflate(R.layout.fragment_statistics, null);
        rescard=(TextView)statistics_fragment.findViewById(R.id.PerformanceRating);
        profAdvice=(CardView)statistics_fragment.findViewById(R.id.card_view);
        imageView=(ImageView)statistics_fragment.findViewById(R.id.winner_loser);
        maintext=(TextView)statistics_fragment.findViewById(R.id.subject_text);
        changeSubject1=(CardView)statistics_fragment.findViewById(R.id.card_view_subject_2);
        changeSubject2=(CardView)statistics_fragment.findViewById(R.id.card_view_subject_3);
        changeSubject3=(CardView)statistics_fragment.findViewById(R.id.card_view_subject_4);
        changeSubject4=(CardView)statistics_fragment.findViewById(R.id.card_view_subject_5);
        changeSubject5=(CardView)statistics_fragment.findViewById(R.id.card_view_subject_6);
        changeSubject1.setOnClickListener(subjectListener);
        changeSubject2.setOnClickListener(subjectListener);
        changeSubject3.setOnClickListener(subjectListener);
        changeSubject4.setOnClickListener(subjectListener);
        changeSubject5.setOnClickListener(subjectListener);
        PieView pieView = (PieView) statistics_fragment.findViewById(R.id.pieView);
        //pieView.setPercentageBackgroundColor(getResources().getColor(R.color.md_blue_800));
        //pieView.setInnerText("A");
        //profAdvice.setVisibility(View.INVISIBLE);
        Random rand=new Random();
        average=rand.nextInt((100-1)+1)+1;
        String te=String.valueOf(average)+"%";
        pieView.setInnerText(te);
        pieView.setPieInnerPadding(40);
        pieView.setInnerBackgroundColor(Color.parseColor("#525962"));
        pieView.setPercentageBackgroundColor(getResources().getColor(R.color.fragment));
        pieView.setmPercentage(average);
        pieView.setMainBackgroundColor(Color.TRANSPARENT);
        hideCurrent();
        if(average<50) {
            imageView.setImageResource(R.drawable.grad2);
            rescard.setText("Try again, Practice Makes a man perfect ");
            rescard.setTextColor(0xff212121);
        } else if(average>=50 & average<70){
            imageView.setImageResource(R.drawable.grad1);
            rescard.setText("You are close but good job");
            rescard.setTextColor(0xff212121);
        } else if(average>=70 & average<101) {
            imageView.setImageResource(R.drawable.grad0);
            rescard.setText("Excellent, Smart Performance Very Good");
            rescard.setTextColor(0xff212121);
        }
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
    public void hideCurrent(){
        String currentSubject;
        currentSubject=maintext.getText().toString();
        if(currentSubject.equals("Maths")) {
            previous = "Maths";
            changeSubject5.setVisibility(View.GONE);
        }else if (currentSubject.equals("Computer Science")) {
            previous = "Computer Science";
            changeSubject3.setVisibility(View.GONE);
        }else if (currentSubject.equals("Physics")) {
            previous = "Physics";
            changeSubject2.setVisibility(View.GONE);
        }else if (currentSubject.equals("Chemistry")) {
            previous = "Chemistry";
            changeSubject1.setVisibility(View.GONE);
        }else if (currentSubject.equals("Biology")) {
            previous = "Biology";
            changeSubject4.setVisibility(View.GONE);
        }
        previous=currentSubject;
    }
    public void showPrevious(){
        if(previous.equals("Maths")) {
            //previous = "Maths";
            changeSubject5.setVisibility(View.VISIBLE);
        }else if (previous.equals("Computer Science")) {
            // previous = "Computer Science";
            changeSubject3.setVisibility(View.VISIBLE);
        }else if (previous.equals("Physics")) {
            // previous = "Physics";
            changeSubject2.setVisibility(View.VISIBLE);
        }else if (previous.equals("Chemistry")) {
            // previous = "Chemistry";
            changeSubject1.setVisibility(View.VISIBLE);
        }else if (previous.equals("Biology")) {
            // previous = "Biology";
            changeSubject4.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Animation animation_clock=AnimationUtils.loadAnimation(getActivity(),R.anim.clockwise);
        rescard.startAnimation(animation_clock);

        Animation animationk=AnimationUtils.loadAnimation(getActivity(),R.anim.right_to_left);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                //buttons[inew][jnew].setBackgroundColor(Color.BLACK);
            }
        }, 2000);
        profAdvice.startAnimation(animationk);
    }
    public View.OnClickListener subjectListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.card_view_subject_2:
                    // Toast.makeText(getActivity(),"Subject 2",Toast.LENGTH_SHORT).show();
                    maintext.setText("Chemistry");
                    showPrevious();
                    hideCurrent();
                    break;
                case R.id.card_view_subject_3:
                    // Toast.makeText(getActivity(),"Subject 3",Toast.LENGTH_SHORT).show();
                    maintext.setText("Physics");
                    showPrevious();
                    hideCurrent();
                    break;
                case R.id.card_view_subject_4:
                    //   Toast.makeText(getActivity(),"Subject 4",Toast.LENGTH_SHORT).show();
                    maintext.setText("Computer Science");
                    showPrevious();
                    hideCurrent();
                    break;
                case R.id.card_view_subject_5:
                    // Toast.makeText(getActivity(),"Subject 5",Toast.LENGTH_SHORT).show();
                    maintext.setText("Biology");
                    showPrevious();
                    hideCurrent();
                    break;
                case R.id.card_view_subject_6:
                    //Toast.makeText(getActivity(),"Subject 6",Toast.LENGTH_SHORT).show();
                    maintext.setText("Maths");
                    showPrevious();
                    hideCurrent();
                    break;
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();

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
