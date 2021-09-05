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

    public static void addComponent(JComponent component) {
        currentScene.getContentPane().add(component);
        currentScene.revalidate();
        currentScene.repaint();
    }

    public static void repaint(){
        currentScene.revalidate();
        currentScene.repaint();
    }

    public static void setComponent(JComponent component) {
        currentScene.getContentPane().removeAll();
        addComponent(component);
    }

    @Override
    public String toString() {
        return "game.SceneManager{" +
                "jFrame=" + currentScene +
                '}';
    }

}
