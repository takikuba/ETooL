package main.java.scenes;

import javax.swing.*;

public class SceneManager {

    private static JFrame currentScene;

    public static void setScene(JFrame currentScene){
        SceneManager.currentScene = currentScene;
    }

    public static JFrame getScene(){
        return currentScene;
    }

}
