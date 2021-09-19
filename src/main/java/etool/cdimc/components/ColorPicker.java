package etool.cdimc.components;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ColorPicker extends JPanel {
    private int red = 0;
    private int blue = 0;
    private int green = 0;
    private final JSlider sliderR;
    private final JSlider sliderB;
    private final JSlider sliderG;
    private final JButton applyButton;
    private String title = null;

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

    public void setPickerBounds(int x, int y) {
        setPickerBounds(x, y, 190, 120);
    }

    private void setPickerBounds(int x, int y, int width, int height) {
        int r = height/14;
        setBounds(x, y, width, height);
        sliderR.setBounds(10, r*3, width - 20, 20);
        sliderB.setBounds(10, r*6, width - 20, 20);
        sliderG.setBounds(10, r*9, width - 20, 20);
        applyButton.setBounds(width/2 - 40, r*12, 80, 20);
    }

    public void applyActionListener(ActionListener actionListener) {
        applyButton.addActionListener(actionListener);
    }

    public void setPickerBorder(String title) {
        this.title = title;
        setBorder(new CompoundBorder(new TitledBorder(title), new EmptyBorder(8, 0, 0, 0)));
    }

    public String getTitle() {
        return title;
    }
}
