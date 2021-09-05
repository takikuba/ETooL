package main.java.scenes;

import javax.swing.*;
import java.awt.*;

public class SceneManager {

    private static JFrame currentScene;
    private static MenuScene menuScene;
    private static WorkspaceScene workspaceScene;

    public static void setMenuAndWorkspace(MenuScene menuScene, WorkspaceScene workspaceScene) {
        SceneManager.menuScene = menuScene;
        SceneManager.workspaceScene = workspaceScene;
    }

    public static void setScene(JFrame currentScene){
        SceneManager.currentScene = currentScene;
    }

    public static JFrame getScene(){
        return currentScene;
    }

    public static void addComponent(JComponent component) {
        currentScene.getContentPane().add(component);
        currentScene.revalidate();
        currentScene.repaint();
    }

    public static void repaint() {
        currentScene.revalidate();
        currentScene.repaint();
    }

    public static void setComponent(JComponent component) {
        currentScene.getContentPane().removeAll();
        addComponent(component);
    }

    public static void repaintColor(Color menuColor, Color workspaceColor) {
        menuScene.setBackground(menuColor);
        workspaceScene.setBackground(workspaceColor);
        repaint();
    }

    @Override
    public String toString() {
        return "game.SceneManager{" +
                "jFrame=" + currentScene +
                '}';
    }

}
