package manila.view.eventView;

import javax.swing.*;
import java.awt.*;

public class PiratePanel extends JPanel {

    public PiratePanel() {

    }

    public void paintComponent(Graphics g) {
        int x = 0, y = 0;

        ImageIcon icon = new ImageIcon("src/image/event/manila.jpg");
        g.drawImage(icon.getImage(), x, y, getSize().width, getSize().height, this);
        while (true) {
            g.drawImage(icon.getImage(), x, y, this);
            if (x > getSize().width && y > getSize().height) break;
            //这段代码是为了保证在窗口大于图片时，图片仍能覆盖整个窗口
            if (x > getSize().width) {
                x = 0;
                y += icon.getIconHeight();
            } else
                x += icon.getIconWidth();
        }
    }

}

