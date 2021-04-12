package com.example.ninemensmorris.file;

import android.content.Context;
import android.util.Log;
import com.example.ninemensmorris.model.GameState;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHandler {

    //Saves gamestate in a serialized object to a file
    public static void saveToFile(Context context, GameState gameState){
        try {
            FileOutputStream fos = context.openFileOutput("GameStateFile", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(gameState);
            os.close();
            fos.close();
            Log.d("fileHandler", "Success writing list to file");
        } catch (Exception e){
            Log.d("fileHandler", "Error writing list to file");
            e.printStackTrace();
        }
    }

    //loads serialized gamestate object from file
    public static GameState readFromFile(Context context) {
        GameState gameState = null;
        try {
            FileInputStream fis = context.openFileInput("GameStateFile");
            ObjectInputStream is = new ObjectInputStream(fis);
            gameState = (GameState) is.readObject();
            is.close();
            fis.close();
            Log.d("fileHandler", "Success reading object from file");
        } catch (Exception e) {
            Log.d("fileHandler", "Error reading object from file");
            e.printStackTrace();
        }
        return gameState;
    }

}
