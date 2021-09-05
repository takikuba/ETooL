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
        clearLogs();
        SceneManager.setScene(this);

        setTitle("ETooL");
        getContentPane().add(new MenuScene());
        getContentPane().add(new WorkspaceScene());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(800, 600);
        setVisible(true);
        getContentPane().add(new MenuScene());
    }

    private void clearLogs(){
        File logFolder = new File(Constants.LOG_FOLDER);
        if(logFolder.length() > 5) {
            File[] logFiles = logFolder.listFiles();
            long oldestDate = Long.MAX_VALUE;
            File oldestFile = null;
            for(File f: logFiles){
                    if(f.lastModified() < oldestDate){
                        oldestDate = f.lastModified();
                        oldestFile = f;
                    }
                }

                if(oldestFile != null){
                    oldestFile.delete();
                }

        }
    }

}
