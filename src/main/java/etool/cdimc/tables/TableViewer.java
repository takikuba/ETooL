package etool.cdimc.tables;

import etool.cdimc.Constants;
import etool.cdimc.models.Table;
import etool.cdimc.repository.Repository;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableViewer extends JPanel {

    private JTable table;

    public TableViewer(Repository repository, String tableName) {
        setBounds(0, 0, 400, 600);
        getModel(repository, tableName);
        table.setBounds(0,20,400,600);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scroll = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(0, 0, 385, 560);
        add(scroll);
        repaint();
        revalidate();
    }

    private void getModel(Repository repository, String tableName) {
        File file = new File(Constants.REPOSITORIES_PATH + repository.getLocation() + "/" + tableName + ".cef");
        String out = usingBufferedReader(file.getPath());
    }

    public String usingBufferedReader(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        String[] headers = new String[1];
        List<String[]> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains("column.")) {
                    sCurrentLine = sCurrentLine.replace("column.", "");
                    headers = sCurrentLine.split("\\$");
                } else {
                    dataList.add(sCurrentLine.split("\\$"));
                }
                contentBuilder.append(sCurrentLine).append("\n");
            }

            Object[][] dataArray = new Object[dataList.size()][];
            for(int i = 0; i < dataList.size(); i++) {
                dataArray[i] = dataList.get(i);
            }
            table = new JTable(dataArray, headers);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
