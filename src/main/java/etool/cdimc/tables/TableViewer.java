package etool.cdimc.tables;

import etool.cdimc.Constants;
import etool.cdimc.models.Table;
import etool.cdimc.repository.Repository;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableViewer extends JPanel {

    private JTable table;

    public TableViewer(Repository repository, String tableName) {
        getModel(repository, tableName);
        table.setBounds(0,20,400,600);
        add(table);
        revalidate();
    }

    private TableModel getModel(Repository repository, String tableName) {
        File file = new File(Constants.REPOSITORIES_PATH + repository.getLocation() + "/" + tableName + ".cef");
        String out = usingBufferedReader(file.getPath());

        return null;
    }

    public String usingBufferedReader(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        boolean header = true;
        String[] headers = new String[0];
        List<String> headersS = new ArrayList<>();
        String[][] data = new String[0][];
        List<String[]> dataS = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if(header) {
                    headers = sCurrentLine.split("/$");
                    header = false;
                } else {
                    dataS.add(sCurrentLine.split("/$"));
                }

//                table = new JTable(dataS, headersS);

                String[] columnNames = {"First Name",
                        "Last Name",
                        "Sport",
                        "# of Years",
                        "Vegetarian"};



                Object[][] data2 = {
                        {"Kathy", "Smith",
                                "Snowboarding", 5, Boolean.FALSE},
                        {"John", "Doe",
                                "Rowing", 3, Boolean.TRUE},
                        {"Sue", "Black",
                                "Knitting", 2, Boolean.FALSE},
                        {"Jane", "White",
                                "Speed reading", 20, Boolean.TRUE},
                        {"Joe", "Brown",
                                "Pool", 10, Boolean.FALSE}
                };

                table = new JTable(data2, columnNames);

                System.out.println(sCurrentLine);
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
