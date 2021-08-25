package main.java;

import main.java.scenes.MenuScene;
import main.java.scenes.SceneManager;

import javax.swing.*;

public class AppRunner extends JFrame {

    public static void main(String[] args) {
        AppRunner appRunner = new AppRunner();
    }

    public AppRunner(){
        SceneManager.setScene(this);

        setTitle("ETooL");

//        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(800, 600);
        setVisible(true);
        getContentPane().add(new MenuScene());
    }

}
