package com.example.ninemensmorris.model;

import java.io.Serializable;

//Object containing all data to save in a file
public class GameState implements Serializable{

    private int[] gameplan;
    private int bluemarker;
    private int redmarker;
    private int turn;
    private int active_phase;
    private int previous_phase;

    public GameState(int[] gameplan, int bluemarker, int redmarker, int turn, int active_phase, int previous_phase){
        this.setGameplan(gameplan);
        this.setBluemarker(bluemarker);
        this.setRedmarker(redmarker);
        this.setTurn(turn);
        this.setActive_phase(active_phase);
        this.setPrevious_phase(previous_phase);
    }


    public int[] getGameplan() {
        return gameplan;
    }

    public void setGameplan(int[] gameplan) {
        this.gameplan = gameplan;
    }

    public int getBluemarker() {
        return bluemarker;
    }

    public void setBluemarker(int bluemarker) {
        this.bluemarker = bluemarker;
    }

    public int getRedmarker() {
        return redmarker;
    }

    public void setRedmarker(int redmarker) {
        this.redmarker = redmarker;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getActive_phase() {
        return active_phase;
    }

    public void setActive_phase(int active_phase) {
        this.active_phase = active_phase;
    }

    public int getPrevious_phase() {
        return previous_phase;
    }

    public void setPrevious_phase(int previous_phase) {
        this.previous_phase = previous_phase;
    }

}
