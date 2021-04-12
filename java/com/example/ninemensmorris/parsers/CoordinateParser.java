package com.example.ninemensmorris.parsers;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.example.ninemensmorris.MainActivity;

public class CoordinateParser {

    private static int pixelLength;

    //gets correct screen size corresponding to orientation and resolution
    public static void init(Context context){
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            pixelLength = context.getResources().getDisplayMetrics().heightPixels;
        else
            pixelLength = context.getResources().getDisplayMetrics().widthPixels;

    }

    //Gets the raw Y coordinates based on gameboard index
    public static int indexToCoordsY(int index){
        int tmp = (int) pixelLength/7;
        switch (index){
            case 3:
            case 6:
            case 9:
                return tmp*0;
            case 2:
            case 5:
            case 8:
                return tmp*1;
            case 1:
            case 4:
            case 7:
                return tmp*2;
            case 24:
            case 23:
            case 22:
            case 10:
            case 11:
            case 12:
                return tmp*3;
            case 19:
            case 16:
            case 13:
                return tmp*4;
            case 20:
            case 17:
            case 14:
                return tmp*5;
            case 21:
            case 18:
            case 15:
                return tmp*6;

            default:
                return -1;
        }
    }

    //Gets the raw X coordinates based on gameboard index
    public static int indexToCoordsX(int index){
        int tmp = (int) pixelLength/7;
        switch (index){
            case 3:
            case 24:
            case 21:
                return tmp*0;
            case 2:
            case 23:
            case 20:
                return tmp*1;
            case 1:
            case 22:
            case 19:
                return tmp*2;
            case 6:
            case 5:
            case 4:
            case 16:
            case 17:
            case 18:
                return tmp*3;
            case 7:
            case 10:
            case 13:
                return tmp*4;
            case 8:
            case 11:
            case 14:
                return tmp*5;
            case 9:
            case 12:
            case 15:
                return tmp*6;

            default:
                return -1;

        }
    }
}
