package etool.cdimc.scenes;

import etool.cdimc.Constants;

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

    public static void repaintFrame() {
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
        repaintFrame();
    }

    public static void setMenuColor(Color color) {
        Constants.setMenuColor(color);
        menuScene.setMenuColor(color);
        repaintFrame();
    }

    public static void setWorkspaceColor(Color color) {
        Constants.setWorkspaceColor(color);
        workspaceScene.setBackground(color);
        menuScene.setWorkspaceColor(color);
        repaintFrame();
    }

    public static void changeFont(String fontType, Color fontColor) {
        if(fontColor!= null) Constants.setFontColor(fontColor);
        if(fontType!= null) Constants.setFont(fontType);
        changeFont(currentScene, Constants.FONT);
    }

    private static void changeFont(Component component, Font font){
        if(component == null) {
            component = currentScene;
        }
        component.setFont(font);
        component.setForeground(Constants.FONT_COLOR);
        if ( component instanceof Container ) {
            for(Component child: ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }

    @Override
    public String toString() {
        return "game.SceneManager{" +
                "jFrame=" + currentScene +
                '}';
    }

}
