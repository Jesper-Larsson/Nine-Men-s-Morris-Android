package com.example.ninemensmorris.model;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.ninemensmorris.MainActivity;


public class GameLogic {

    private static int active_phase;
    private static int previous_phase;
    private static int lastIndex;
    public static final int PLACE = 1;
    public static final int MOVE = 2;
    public static final int REMOVE = 3;
    public static final int MARK = 4;

    public static void initGameLogic(){
        active_phase = 1;
        previous_phase = 1;
        lastIndex = -1;
    }

    //executes game turn and returns the action UI should perform
    //returns -1 if invalid
    public static int executeTurn(int index, int x, int y){

        if (active_phase == PLACE) {
            if (placeMarker(index, x, y)) {
                if (MainActivity.getGameRules().isMovePhase() && active_phase == PLACE)
                    active_phase = MARK;
                if (MainActivity.getGameRules().isThreeInARow(index)) {
                    previous_phase = active_phase;
                    active_phase = REMOVE;
                }
                return PLACE;
            }
        }
        else if (active_phase == MARK){
            if (MainActivity.getGameRules().getTurn() == MainActivity.getGameRules().boardSlotStatus(index)){
                lastIndex = index;
                active_phase = MOVE;
                return MARK;
            }
        }
        else if (active_phase == MOVE) {
            if (MainActivity.getGameRules().isFly()){
                if (MainActivity.getGameRules().legalFlyMove(index, lastIndex)){
                    active_phase = MARK;
                    if (MainActivity.getGameRules().isThreeInARow(index)) {
                        previous_phase = MARK;
                        active_phase = REMOVE;
                    }
                    return MOVE;
                }
            }

            if (MainActivity.getGameRules().legalMove(index, lastIndex)) {
                active_phase = MARK;
                if (MainActivity.getGameRules().isThreeInARow(index)) {
                    previous_phase = MARK;
                    active_phase = REMOVE;
                }
                return MOVE;
            } else if (MainActivity.getGameRules().getTurn() == MainActivity.getGameRules().boardSlotStatus(index)){
                lastIndex = index;
                active_phase = MOVE;
                return MARK;
            }
        }
        else if (active_phase == REMOVE) {
            if (MainActivity.getGameRules().remove(index, MainActivity.getGameRules().getTurn())) {
                active_phase = previous_phase;
                previous_phase = REMOVE;
                return REMOVE;
            }
        }
        return -1;
        }

    private static boolean placeMarker(int index, int x, int y){
        if (x >= 0 && y >= 0) {
            if (MainActivity.getGameRules().legalMove(index, 0)){
                return true;
            }
        }
        return false;
    }

    //initializes game from saved state loaded from file
    public static void initGame(int active_phaseNew, int previous_phaseNew){
        active_phase = active_phaseNew;
        previous_phase = previous_phaseNew;
    }

    //resets gamestate variables so a new game can begin
    public static GameState newGame(){
        int[] gameplan = new int[25];
        int bluemarker, redmarker;
        bluemarker = redmarker = 9;
        int turn = NineMenMorrisRules.RED_MOVES;
        int active_phase = GameLogic.PLACE;
        int previous_phase = GameLogic.PLACE;
        GameState gameState = new GameState(gameplan, bluemarker, redmarker, turn, active_phase, previous_phase);
        return gameState;
    }


    public static int getLastIndex(){
        return lastIndex;
    }

    public static int getActive_phase(){
        return active_phase;
    }

    public static int getPrevious_phase() {return previous_phase; }
}
