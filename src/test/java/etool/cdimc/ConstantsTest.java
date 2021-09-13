package etool.cdimc;

import org.testng.annotations.Test;

import java.awt.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConstantsTest {

    @Test
    public void shouldProperlySetUpBackgroundColors() {
        Constants.setMenuColor(Color.BLUE);
        assertThat(Constants.MENU_COLOR).isEqualTo(Color.BLUE);

        Constants.setWorkspaceColor(Color.RED);
        assertThat(Constants.WORKSPACE_COLOR).isEqualTo(Color.RED);
    }

    @Test
    public void shouldProperlySetUpFont() {
        Constants.setFontColor(Color.BLACK);
        assertThat(Constants.FONT_COLOR).isEqualTo(Color.BLACK);

        Constants.setFont("Arial");
        assertThat(Constants.FONT).isEqualTo(new Font("Arial", Font.PLAIN, 10));
    }

}