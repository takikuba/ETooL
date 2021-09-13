package etool.cdimc;

import etool.cdimc.scenes.MenuScene;
import etool.cdimc.scenes.SceneManager;
import etool.cdimc.scenes.WorkspaceScene;

import javax.swing.*;
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
