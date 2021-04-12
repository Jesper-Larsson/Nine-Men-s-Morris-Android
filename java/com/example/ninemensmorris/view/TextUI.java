package com.example.ninemensmorris.view;

import com.example.ninemensmorris.MainActivity;
import com.example.ninemensmorris.R;
import com.example.ninemensmorris.model.GameLogic;
import com.example.ninemensmorris.model.NineMenMorrisRules;

public class TextUI {

    //Updates the TextView according to which players turn it is and their next action
    public static void updateTextView(){
        int tmp = 1;
        if (MainActivity.getGameRules().getTurn() == 1)
            tmp = 2;
        if (MainActivity.getGameRules().win(tmp)){
            if (MainActivity.getGameRules().getTurn() == 1){
                MainActivity.getTurnView().setText(R.string.red_wins_text);
            } else{
                MainActivity.getTurnView().setText(R.string.blue_wins_text);
            }
            MainActivity.setGameRunning(false);
            return;
        }
        switch(GameLogic.getActive_phase()) {
            case 1:
                if (MainActivity.getGameRules().getTurn() == NineMenMorrisRules.RED_MOVES)
                    MainActivity.getTurnView().setText(R.string.red_place_text);
                else
                    MainActivity.getTurnView().setText(R.string.blue_place_text);
                return;
            case 2:
            case 4:
                if (MainActivity.getGameRules().getTurn() == NineMenMorrisRules.RED_MOVES)
                    MainActivity.getTurnView().setText(R.string.red_make_move_text);
                else
                    MainActivity.getTurnView().setText(R.string.blue_make_move_text);
                return;
            case 3:
                if (MainActivity.getGameRules().getTurn() == NineMenMorrisRules.RED_MOVES)
                    MainActivity.getTurnView().setText(R.string.blue_remove_text);
                else
                    MainActivity.getTurnView().setText(R.string.red_remove_text);
                return;
            default:
                return;
        }

    }
}
