package etool.cdimc.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ColorPicker extends JPanel {
    private int red = 0;
    private int blue = 0;
    private int green = 0;
    private JSlider sliderR;
    private JSlider sliderB;
    private JSlider sliderG;
    private JButton applyButton;

    public ColorPicker(){
        setLayout(null);

        sliderR = new JSlider(0, 255);
        sliderR.addChangeListener(e -> {
            red = sliderR.getValue();
            setBackground(new Color(red, green, blue));
        });
        sliderB = new JSlider(0, 255);
        sliderB.addChangeListener(e -> {
            blue = sliderB.getValue();
            setBackground(new Color(red, green, blue));
        });
        sliderG = new JSlider(0, 255);
        sliderG.addChangeListener(e -> {
            green = sliderG.getValue();
            setBackground(new Color(red, green, blue));
        });

        add(sliderB);
        add(sliderR);
        add(sliderG);

        applyButton = new JButton("Apply");
        add(applyButton);
    }

    public void setPickerBounds(int x, int y, int width, int height) {
        int r = height/14;
        setBounds(x, y, width, height);
        sliderR.setBounds(10, r*3, width - 20, 20);
        sliderB.setBounds(10, r*6, width - 20, 20);
        sliderG.setBounds(10, r*9, width - 20, 20);
        applyButton.setBounds(width/2 - 40, r*12, 80, 20);
    }

    public void addApplyActionListener(ActionListener actionListener) {
        applyButton.addActionListener(actionListener);
    }
}
