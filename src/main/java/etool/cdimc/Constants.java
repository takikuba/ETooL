package etool.cdimc;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Constants {

    public static Color MENU_COLOR = Color.LIGHT_GRAY;
    public static Color WORKSPACE_COLOR = Color.GRAY;
    public static Font FONT = new Font("TimesRoman", Font.PLAIN, 10);
    public static Color FONT_COLOR = Color.BLACK;

    public static String LOGO_PATH = "src/main/resources/logo2.png";
    public static String LOG_FOLDER = "src/test/resources/logs";

    private static final Logger logger = setUpLogger();

    public static Logger logger() {
        return logger;
    }

    public static void setMenuColor(Color color) {
        MENU_COLOR = color;
    }

    public static void setWorkspaceColor(Color color) {
        WORKSPACE_COLOR = color;
    }

    public static void setFont(String font) {
        Constants.FONT = new Font(font, Font.PLAIN, 10);
    }

    public static void setFontColor(Color fontColor) {
        Constants.FONT_COLOR = fontColor;
    }

    private static Logger setUpLogger() {
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;
        try {
            String logFileName = new SimpleDateFormat("'log'yyyyMMddHHmm'.log'").format(new Date());
            File logFile = new File(Constants.LOG_FOLDER + '\\' + logFileName);
            if(logFile.getParentFile().listFiles().length >= 5){
                clearLogs(logFile);
            }
            logFile.createNewFile();
            fh = new FileHandler(logFile.getPath());
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info("Initialization of logger!");

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        return logger;
    }

    private static void clearLogs(File file){
        File[] logFiles = file.getParentFile().listFiles();
        long oldestDate = Long.MAX_VALUE;
        File oldestFile = null;
        if (logFiles != null) {
            for (File f : logFiles) {
                if (f.lastModified() < oldestDate) {
                    oldestDate = f.lastModified();
                    oldestFile = f;
                }
            }
            if (oldestFile != null) {
                oldestFile.delete();
            }
        }
    }

}
