package etool.cdimc.scenes;

import etool.cdimc.Constants;
import etool.cdimc.components.ColorPicker;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings extends JFrame {
    private final Logger logger = Constants.logger();
    private ColorPanel colorPanel;
    private FontPanel fontPanel;

    Settings() {
        logger.log(Level.INFO, "Settings");
        setTitle("Settings");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setResizable(false);
        setBackground(Constants.MENU_COLOR);
        setSize(400, 295);
        setVisible(true);
        setLocationRelativeTo(this.getParent());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.33;
        gbc.fill = GridBagConstraints.BOTH;
        add((colorPanel = new ColorPanel()), gbc);

        gbc.gridx = 1;
        gbc.weighty = 0.33;
        gbc.weightx = 0.5;
        add((fontPanel = new FontPanel()), gbc);

    }

    public void repaintFrame() {
        this.revalidate();
        this.repaint();
    }

    class ColorPanel extends JPanel {
        private JPanel menuPanel;
        private JPanel workspacePanel;

        public ColorPanel() {
            logger.log(Level.INFO, "Color choose setting");

            setLayout(null);

            ColorPicker colorPickerM = new ColorPicker();
            colorPickerM.setPickerBounds(0,5,190,120);
            colorPickerM.setBorder(new CompoundBorder(new TitledBorder("Menu Color"), new EmptyBorder(8, 0, 0, 0)));
            colorPickerM.addApplyActionListener(e -> SceneManager.setMenuColor(colorPickerM.getBackground()));
            add(colorPickerM);

            ColorPicker colorPickerW = new ColorPicker();
            colorPickerW.setPickerBounds(0,130, 190,120);
            colorPickerW.setBorder(new CompoundBorder(new TitledBorder("Workspace Color"), new EmptyBorder(8, 0, 0, 0)));
            colorPickerW.addApplyActionListener(e -> SceneManager.setWorkspaceColor(colorPickerW.getBackground()));
            add(colorPickerW);

            setBackground(Constants.MENU_COLOR);
        }
    }

    class FontPanel extends JPanel {

        public FontPanel() {
            logger.log(Level.INFO, "Font choose setting");

            setLayout(null);

            ColorPicker colorPickerT = new ColorPicker();
            colorPickerT.setPickerBounds(0,5,190,120);
            colorPickerT.setBorder(new CompoundBorder(new TitledBorder("Text Color"), new EmptyBorder(8, 0, 0, 0)));
            colorPickerT.addApplyActionListener(e -> {
                Constants.setFontColor(colorPickerT.getBackground());
                SceneManager.changeFont(null, Constants.FONT);
            });
            add(colorPickerT);

            JPanel fontChooser = new JPanel();
            fontChooser.setBounds(0,130,190,120);
            fontChooser.setBorder(new CompoundBorder(new TitledBorder("Font Chooser"), new EmptyBorder(8, 0, 0, 0)));
            add(fontChooser);

            setBackground(Constants.MENU_COLOR);
        }

    }

}
