package main.java;

import main.java.scenes.MenuScene;
import main.java.scenes.SceneManager;
import main.java.scenes.WorkspaceScene;

import javax.swing.*;
import java.io.File;
import java.util.logging.Logger;

public class AppRunner extends JFrame {
    private static final Logger logger = Constants.logger();

    public static void main(String[] args) {
        logger.info("Application running!");
        new AppRunner();
    }

    public AppRunner(){
        SceneManager.setScene(this);

        setTitle("ETooL");

        MenuScene menuScene = new MenuScene();
        WorkspaceScene workspaceScene = new WorkspaceScene();
        SceneManager.setMenuAndWorkspace(menuScene, workspaceScene);

        getContentPane().add(menuScene);
        getContentPane().add(workspaceScene);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
