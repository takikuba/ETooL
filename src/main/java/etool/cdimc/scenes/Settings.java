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
        add(new ColorPanel(), gbc);

        gbc.gridx = 1;
        gbc.weighty = 0.33;
        gbc.weightx = 0.5;
        add(new FontPanel(), gbc);

    }

    class ColorPanel extends JPanel {

        public ColorPanel() {
            logger.log(Level.INFO, "Color choose setting");

            setLayout(null);
            add(getMenuColorPicker());
            add(getWorkspaceColorPicker());
            setBackground(Constants.MENU_COLOR);
        }

        private ColorPicker getMenuColorPicker(){
            ColorPicker colorPicker = getColorPicker(5, "MenuColor");
            colorPicker.applyActionListener(e -> SceneManager.setMenuColor(colorPicker.getBackground()));
            return colorPicker;
        }

        private ColorPicker getWorkspaceColorPicker(){
            ColorPicker colorPicker = getColorPicker(130, "Workspace Color");
            colorPicker.applyActionListener(e -> SceneManager.setWorkspaceColor(colorPicker.getBackground()));
            return colorPicker;
        }
    }

    class FontPanel extends JPanel {

        public FontPanel() {
            logger.log(Level.INFO, "Font choose setting");
            setLayout(null);

            ColorPicker colorPickerT = getColorPicker(5, "Text Color");
            colorPickerT.applyActionListener(e -> SceneManager.changeFont(null, colorPickerT.getBackground()));
            add(colorPickerT);

            add(getFontChooser());
            setBackground(Constants.MENU_COLOR);
        }

        private JPanel getFontChooser(){
            JPanel fontChooser = new JPanel();
            fontChooser.setBounds(0,130,190,120);
            fontChooser.setBorder(new CompoundBorder(new TitledBorder("Font Chooser"), new EmptyBorder(8, 0, 0, 0)));

            fontChooser.add(getFontList());
            return fontChooser;
        }

        private JScrollPane getFontList(){
            JList<String> fontList = new JList(Constants.FONTS_LIST.toArray());
            fontList.setFixedCellWidth(100);
            fontList.setFixedCellHeight(15);
            fontList.addListSelectionListener(l -> SceneManager.changeFont(fontList.getSelectedValue(), null));
            fontList.setVisibleRowCount(5);

            JScrollPane listScroller = new JScrollPane(fontList);
            listScroller.setBounds(0,0,100, 80);
            return listScroller;
        }

    }

    ColorPicker getColorPicker(int yPosition, String title){
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setPickerBounds(0,yPosition);
        colorPicker.setPickerBorder(title);
        return colorPicker;
    }
}
