package com.example.ninemensmorris;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

public class TouchView extends View {

    private static int x;
    private static int y;
    private float pixelLength;
    private static int lastPressedIndex;
    private static Context mainContext;

    public static void initTouchView(Context context){
        mainContext = context;
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            pixelLength = context.getResources().getDisplayMetrics().heightPixels;
        else
            pixelLength = context.getResources().getDisplayMetrics().widthPixels;
        Log.i("touchEvent", "pixelWidth: " + pixelLength);
    }

    //Returns the index on the gameboard that corresponds to where the user clicked
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case ACTION_DOWN:
            case ACTION_MOVE:
                return true;

            case ACTION_UP:
                x = (int) event.getX();
                y = (int) event.getY();
                x = (int) (x/(pixelLength /7));
                y = (int) (y/(pixelLength /7));
                lastPressedIndex = pixelToGridIndex();

                sendIndexToMainActivity(lastPressedIndex);
                invalidate();
                return true;
            default:
                return false;
        }
    }

    //creates intent and sends data to MainActivity
    private void sendIndexToMainActivity(int index){
        Intent intent = new Intent();
        intent.setAction(MainActivity.MyBroadcastReceiver.ACTION);
        intent.putExtra("dataToPass", index);
        mainContext.sendBroadcast(intent);
    }

    //Converts pixel coordinates to a game board index
    public int pixelToGridIndex(){
        switch (x){
            case 0:
                switch (y){
                    case 0:
                        return 3;
                    case 3:
                        return 24;
                    case 6:
                        return 21;
                    default:
                        return 0;
                }
            case 1:
                switch (y){
                    case 1:
                        return 2;
                    case 3:
                        return 23;
                    case 5:
                        return 20;
                    default:
                        return 0;
                }
            case 2:
                switch(y){
                    case 2:
                        return 1;
                    case 3:
                        return 22;
                    case 4:
                        return 19;
                    default:
                        return 0;
                }
            case 3:
                switch(y){
                    case 0:
                        return 6;
                    case 1:
                        return 5;
                    case 2:
                        return 4;
                    case 4:
                        return 16;
                    case 5:
                        return 17;
                    case 6:
                        return 18;
                    default:
                        return 0;
                }
            case 4:
                switch(y){
                    case 2:
                        return 7;
                    case 3:
                        return 10;
                    case 4:
                        return 13;
                    default:
                        return 0;
                }
            case 5:
                switch(y){
                    case 1:
                        return 8;
                    case 3:
                        return 11;
                    case 5:
                        return 14;
                    default:
                        return 0;
                }
            case 6:
                switch(y){
                    case 0:
                        return 9;
                    case 3:
                        return 12;
                    case 6:
                        return 15;
                    default:
                        return 0;
                }
        }
        return 0;
    }
}