package etool.cdimc;

import etool.cdimc.components.ColorPicker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ColorPickerTest {
    private ColorPicker picker;

    @BeforeMethod
    public void setUp() {
        picker = new ColorPicker();
    }

    @Test
    public void testSetPickerBounds() {
        picker.setPickerBounds(0, 100);
        assertThat(picker.getLocation()).isEqualTo(new Point(0, 100));
        assertThat(picker.getHeight()).isEqualTo(120);
        assertThat(picker.getWidth()).isEqualTo(190);
    }

    @Test
    public void testSetPickerBorder() {
        picker.setPickerBorder("Title");
        assertThat(picker.getTitle()).isEqualTo("Title");
    }
}