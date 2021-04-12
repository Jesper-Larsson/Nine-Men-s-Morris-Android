package com.example.ninemensmorris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ninemensmorris.file.FileHandler;
import com.example.ninemensmorris.model.GameState;
import com.example.ninemensmorris.model.NineMenMorrisRules;
import com.example.ninemensmorris.model.GameLogic;
import com.example.ninemensmorris.parsers.CoordinateParser;
import com.example.ninemensmorris.view.GameUI;
import com.example.ninemensmorris.view.TextUI;

public class MainActivity extends AppCompatActivity {

    private static ImageView[] blueMarkerList;
    private static ImageView[] redMarkerList;
    private static ImageView highlightMarkerView;
    private static int[] gameplanIndex;
    private static boolean gameRunning;

    private static MyBroadcastReceiver receiver;
    private static NineMenMorrisRules gameRules;
    private static ImageView gameboardView;
    private static TextView turnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReceiver(new MyBroadcastReceiver());
        this.registerReceiver(getReceiver(), new IntentFilter(MyBroadcastReceiver.ACTION));
        setGameboardView(findViewById(R.id.gameboardView));
        setTurnView(findViewById(R.id.turnView));
        getTurnView().setText(R.string.red_place_text);

        setGameRunning(true);
        setGameRules(new NineMenMorrisRules());
        scaleGameboardView();
        CoordinateParser.init(this);
        GameLogic.initGameLogic();
        TouchView.initTouchView(this);

        setBlueMarkerList(new ImageView[9]);
        setRedMarkerList(new ImageView[9]);
        setGameplanIndex(new int[25]);
        setHighlightMarkerView(findViewById(R.id.highlightMarkerView));
    }

    //save gamestate to file when app stops
    @Override
    protected void onStop() {
        Log.i("stateChange", "ON STOP");
        super.onStop();
        GameState gameState = new GameState(getGameRules().getGameplan(), getGameRules().getBluemarker(),
                getGameRules().getRedmarker(), getGameRules().getTurn(), GameLogic.getActive_phase(),
                GameLogic.getPrevious_phase());
        FileHandler.saveToFile(this, gameState);
    }

    //load previous gamestate from file
    //start new if no previous state is saved
    @Override
    protected void onStart() {
        super.onStart();
        GameState gameState = FileHandler.readFromFile(this);
        if (gameState != null)
            initGame(gameState);
        else
            GameLogic.newGame();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(getReceiver());
    }
    //create game logic and viewcomponents
    public void initGame(GameState gameState){
        getGameRules().initGame(gameState.getGameplan(), gameState.getBluemarker(), gameState.getRedmarker(), gameState.getTurn());
        GameLogic.initGame(gameState.getActive_phase(), gameState.getPrevious_phase());
        ConstraintLayout myLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        GameUI.initUi(this, myLayout);
        TextUI.updateTextView();
    }

    //scales the gameboard based on screen size and orientation
    private void scaleGameboardView() {
        ImageView gameboardView = findViewById(R.id.gameboardView);
        int pixelLength;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            pixelLength = this.getResources().getDisplayMetrics().heightPixels;
            getSupportActionBar().hide();
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else
            pixelLength = this.getResources().getDisplayMetrics().widthPixels;
        gameboardView.requestLayout();
        gameboardView.getLayoutParams().height = pixelLength;
        gameboardView.getLayoutParams().width = pixelLength;
    }

    //creates a clean gameboard when new game button is pressed
    public void newGameButtonPress(View view) {
        GameState gameState = GameLogic.newGame();
        initGame(gameState);
        GameUI.clearBoard();
        MainActivity.getHighlightMarkerView().setVisibility(View.INVISIBLE);
        MainActivity.setGameRunning(true);
    }

    //reads input from user onto gameboard
    //executes game logic based on click data
    public class MyBroadcastReceiver extends BroadcastReceiver {
        public static final String ACTION = "com.example.ACTION_SEND_INDEX";

        @Override
        public void onReceive(Context context, Intent intent) {
            int index = intent.getIntExtra("dataToPass", 0);
            if (index != 0 && isGameRunning()) {
                int x = CoordinateParser.indexToCoordsX(index);
                int y = CoordinateParser.indexToCoordsY(index);
                int action = GameLogic.executeTurn(index, x, y);

                if (action != -1) {
                    if (action == GameLogic.PLACE) {
                        ConstraintLayout myLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
                        GameUI.placeMarkerOnUi(x, y, index, getApplicationContext(), myLayout);
                    } else if (action == GameLogic.REMOVE) {
                        GameUI.removeMarkerFromUi(index);
                    } else if (action == GameLogic.MOVE) {
                        GameUI.moveMarker(index, x, y);
                    } else if (action == GameLogic.MARK){
                        GameUI.highlightMarker(x, y);
                    }
                    TextUI.updateTextView();
                }
            }
        }
    }

    public static ImageView[] getBlueMarkerList() {
        return blueMarkerList;
    }
    public static void setBlueMarkerList(ImageView[] blueMarkerList) {
        MainActivity.blueMarkerList = blueMarkerList;
    }
    public static ImageView[] getRedMarkerList() {
        return redMarkerList;
    }
    public static void setRedMarkerList(ImageView[] redMarkerList) {
        MainActivity.redMarkerList = redMarkerList;
    }
    public static ImageView getHighlightMarkerView() {
        return highlightMarkerView;
    }
    public static void setHighlightMarkerView(ImageView highlightMarkerView) {
        MainActivity.highlightMarkerView = highlightMarkerView;
    }
    public static int[] getGameplanIndex() {
        return gameplanIndex;
    }
    public static void setGameplanIndex(int[] gameplanIndex) {
        MainActivity.gameplanIndex = gameplanIndex;
    }
    public static boolean isGameRunning() {
        return gameRunning;
    }
    public static void setGameRunning(boolean gameRunning) {
        MainActivity.gameRunning = gameRunning;
    }
    public static MyBroadcastReceiver getReceiver() {
        return receiver;
    }
    public static void setReceiver(MyBroadcastReceiver receiver) {
        MainActivity.receiver = receiver;
    }
    public static NineMenMorrisRules getGameRules() {
        return gameRules;
    }
    public static void setGameRules(NineMenMorrisRules gameRules) {
        MainActivity.gameRules = gameRules;
    }
    public static ImageView getGameboardView() {
        return gameboardView;
    }
    public static void setGameboardView(ImageView gameboardView) {
        MainActivity.gameboardView = gameboardView;
    }
    public static TextView getTurnView() {
        return turnView;
    }
    public static void setTurnView(TextView turnView) {
        MainActivity.turnView = turnView;
    }
}

