package Main4;
import java.awt.*;
import javax.swing.*;

public class DoubleIcon implements Icon {
    private Icon icon1, icon2;

    public DoubleIcon(Icon icon1, Icon icon2) {
        this.icon1 = icon1;
        this.icon2 = icon2;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        icon1.paintIcon(c, g, x + 40, -y + 40);
        icon2.paintIcon(c, g, x +40, y + icon1.getIconHeight()-40 ); // muon doi sang horizontal thi ..., x+getIconwidth, y
    }

    public int getIconWidth() {
        return icon1.getIconWidth() + icon2.getIconWidth();
    }

    public int getIconHeight() {
        return Math.max(icon1.getIconHeight(), icon2.getIconHeight());
    }
}
