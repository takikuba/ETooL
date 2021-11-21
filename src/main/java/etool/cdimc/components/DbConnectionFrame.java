package etool.cdimc.components;

import etool.cdimc.Constants;
import etool.cdimc.connectors.DbConnectors;

import javax.swing.*;
import java.sql.SQLException;

public class DbConnectionFrame extends JFrame {

    private final JButton testConnection = new JButton("Test");
    private final JButton connect = new JButton("Connect");
    private JLabel iconLabel;
    private JTextField url;
    private JTextField user;
    private JTextField password;
    private DbConnectors connector;
    private final DbTransformFrame parent;

    public DbConnectionFrame(DbTransformFrame parent) {
        this.parent = parent;
        setTitle("DB connect");
        setSize(270, 370);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(null);
        setBackground(Constants.WORKSPACE_COLOR);
        setResizable(false);
        addPanel();
    }

    private void addPanel() {
        JPanel connectionPanel = new JPanel();
        connectionPanel.setBackground(Constants.MENU_COLOR);
        connectionPanel.setBounds(0, 0, 270, 370);
        connectionPanel.setLayout(null);

        DbConnectors defaultConnector = DbConnectors.ORACLE;
        connector = defaultConnector;
        JComboBox<DbConnectors> box = new JComboBox<>(DbConnectors.values());
        box.setSelectedItem(defaultConnector);

        iconLabel = new JLabel(defaultConnector.getIcon());
        url = new JTextField(defaultConnector.getUrl());
        user = new JTextField(defaultConnector.getUser());
        password = new JTextField(defaultConnector.getPassword());

        box.addItemListener( a -> {
            DbConnectors db = (DbConnectors) box.getSelectedItem();
            if(db != null) {
                connector = db;
                changeDBInput(db);
            }
        });

        testConnection.addActionListener( e -> testConnection(connector));
        connect.addActionListener(c -> {
            try {
                connect(connector);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });


        box.setBounds(130, 50, 110, 30);
        iconLabel.setBounds(10, 10, 100, 100);
        url.setBounds(10, 130, 235, 30);
        user.setBounds(10, 170, 235, 30);
        password.setBounds(10, 210, 235, 30);
        connect.setBounds(130, 300, 100, 20);
        testConnection.setBounds(25, 300, 100, 20);

        connectionPanel.add(iconLabel);
        connectionPanel.add(box);
        connectionPanel.add(url);
        connectionPanel.add(user);
        connectionPanel.add(password);
        connectionPanel.add(testConnection);
        connectionPanel.add(connect);

        getContentPane().add(connectionPanel);
        setVisible(true);
    }

    private void changeDBInput(DbConnectors db) {
        iconLabel.setIcon(db.getIcon());
        url.setText(db.getUrl());
        user.setText(db.getUser());
        password.setText(db.getPassword());
    }

    private void connect(DbConnectors db) throws SQLException, ClassNotFoundException {
        if(testConnection(db)) {
            parent.startTransform(db.getParser().setConnection(db.getConnector().connect(url.getText(), user.getText(), password.getText())));
            this.dispose();
        }
    }

    private boolean testConnection(DbConnectors db) {
        if(db.getConnector().testConnection(url.getText(), user.getText(), password.getText())){
            JOptionPane.showMessageDialog(null, "Connection successful!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Connection failed!");
            return false;
        }
    }

    public DbConnectors getConnector() {
        return connector;
    }
}