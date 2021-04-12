package com.example.ninemensmorris.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.ninemensmorris.MainActivity;
import com.example.ninemensmorris.R;
import com.example.ninemensmorris.model.GameLogic;
import com.example.ninemensmorris.model.NineMenMorrisRules;
import com.example.ninemensmorris.parsers.CoordinateParser;

public class GameUI {

    //places new marker on the chosen index
    public static void placeMarkerOnUi(int x, int y, int index, Context context, ConstraintLayout myLayout){
        try {
            ImageView newMarker = new ImageView(context);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams
                    (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            newMarker.setLayoutParams(params);
            newMarker.setX(x);
            newMarker.setY(y);
            Log.i("GameRules", "turn: " + MainActivity.getGameRules().getTurn());
            if (MainActivity.getGameRules().getTurn() == NineMenMorrisRules.RED_MOVES) {
                newMarker.setImageResource(R.drawable.ic_baseline_marker_blue);
                MainActivity.getGameplanIndex()[index] = 8 - MainActivity.getGameRules().getBluemarker();
                MainActivity.getBlueMarkerList()[8 - MainActivity.getGameRules().getBluemarker()] = newMarker;
            } else {
                newMarker.setImageResource(R.drawable.ic_baseline_marker_red);
                MainActivity.getGameplanIndex()[index] = 8 - MainActivity.getGameRules().getRedmarker();
                MainActivity.getRedMarkerList()[8 - MainActivity.getGameRules().getRedmarker()] = newMarker;
            }
            myLayout.addView(newMarker);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //remove existing marker from index
    public static void removeMarkerFromUi(int index){

        int markerIndex = MainActivity.getGameplanIndex()[index];
        int turn = MainActivity.getGameRules().getTurn();
        if (turn == NineMenMorrisRules.RED_MOVES){
            MainActivity.getRedMarkerList()[markerIndex].setVisibility(View.INVISIBLE);
            Log.i("MainActivity", "Removing red marker: " + markerIndex);
        } else{
            MainActivity.getBlueMarkerList()[markerIndex].setVisibility(View.INVISIBLE);
            Log.i("MainActivity", "Removing blue marker: " + markerIndex);
        }
    }

    //animates existing marker to new place
    public static void moveMarker(int index, int x, int y){
        int tmp = MainActivity.getGameplanIndex()[GameLogic.getLastIndex()];
        ImageView iw;
        if (MainActivity.getGameRules().getTurn() == NineMenMorrisRules.RED_MOVES)
            iw = MainActivity.getBlueMarkerList()[tmp];
        else
            iw = MainActivity.getRedMarkerList()[tmp];
        MainActivity.getGameplanIndex()[index] = tmp;
        MainActivity.getGameplanIndex()[GameLogic.getLastIndex()] = 0;

        AnimatorSet move = new AnimatorSet();
        move
                .play(ObjectAnimator.ofFloat(iw,
                        "x"
                        , x))
                .with(ObjectAnimator.ofFloat(iw,
                        "y"
                        , y));
        move.setDuration(500);
        move.start();

        MainActivity.getHighlightMarkerView().setVisibility(View.INVISIBLE);
    }

    //casts a shadow to highlight which marker is chosen
    public static void highlightMarker(int x, int y){
        MainActivity.getHighlightMarkerView().setVisibility(View.VISIBLE);
        MainActivity.getHighlightMarkerView().setX(x);
        MainActivity.getHighlightMarkerView().setY(y);
    }

    //clears board from all markers
    public static void clearBoard(){
        for (int i=0;i<MainActivity.getRedMarkerList().length;i++){
            if (MainActivity.getRedMarkerList()[i] != null)
                MainActivity.getRedMarkerList()[i].setVisibility(View.INVISIBLE);
        }
        for (int i=0;i<MainActivity.getBlueMarkerList().length;i++){
            if (MainActivity.getBlueMarkerList()[i] != null)
                MainActivity.getBlueMarkerList()[i].setVisibility(View.INVISIBLE);
        }
    }

    //Initializes existing game state from file
    public static void initUi(Context context, ConstraintLayout myLayout){
        int redCounter = 0;
        int blueCounter = 0;
        int[] tmp = MainActivity.getGameRules().getGameplan();
        for  (int i=0;i<tmp.length;i++){
            if (tmp[i] == 1) { //blue
                placeOnUiFromFile(1, i, blueCounter, context, myLayout);
                blueCounter++;
            } else if (tmp[i] == 2){
                placeOnUiFromFile(2, i, redCounter, context, myLayout);
                redCounter++;
            }
        }
    }

    //places marker on UI, loaded from file
    private static void placeOnUiFromFile(int color, int index, int counter, Context context, ConstraintLayout myLayout){
        try {
            ImageView newMarker = new ImageView(context);
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams
                    (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            int x = CoordinateParser.indexToCoordsX(index);
            int y = CoordinateParser.indexToCoordsY(index);
            newMarker.setLayoutParams(params);
            newMarker.setX(x);
            newMarker.setY(y);

            if (color == NineMenMorrisRules.BLUE_MOVES) {
                newMarker.setImageResource(R.drawable.ic_baseline_marker_blue);
                MainActivity.getGameplanIndex()[index] = counter;
                MainActivity.getBlueMarkerList()[counter] = newMarker;
            } else {
                newMarker.setImageResource(R.drawable.ic_baseline_marker_red);
                MainActivity.getGameplanIndex()[index] = counter;
                MainActivity.getRedMarkerList()[counter] = newMarker;
            }

            myLayout.addView(newMarker);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
