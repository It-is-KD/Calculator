package com.example.calculator;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class DetectSwipeGestureListener extends GestureDetector.SimpleOnGestureListener implements GestureDetector.OnGestureListener
{

    private static int min_swipeX = 100;
    private static int max_swipeX = 500;

    private MainActivity activity = null;

    public MainActivity getActivity() { return activity; }

    public void setActivity(MainActivity activity) { this.activity=activity; }

    @Override
    public boolean onFling(MotionEvent e1,MotionEvent e2,float velocityX,float velocityY)
    {
        float deltaX = e1.getX() - e2.getX();
        float delX = Math.abs(deltaX);

        System.out.print("SWIPE 1");

        if(delX>=min_swipeX && delX<=max_swipeX)
        {
            System.out.print("SWIPE 2");

            if(delX<0)
            {
                System.out.print("SWIPE WORKS");
                this.activity.backspace("Swipe Success");
            }
        }
        return true;
    }
}
