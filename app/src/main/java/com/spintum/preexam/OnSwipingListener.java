package com.spintum.preexam;

import android.content.Context;
import android.view.GestureDetector;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by nirmal on 1/12/16.
 */

public class OnSwipingListener implements View.OnTouchListener{
    private final GestureDetector gestureDetector;
    public OnSwipingListener(Context context){
        gestureDetector = new GestureDetector(context,new GestureListener());
    }
    public void onSwipeLeft(){

    }
    public void onSwipeRight(){

    }
    public boolean onTouch(View v, MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener{
        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight();
                else
                    onSwipeLeft();
                return true;
            }
            return false;
        }

    }

}
