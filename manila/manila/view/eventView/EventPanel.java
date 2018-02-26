package manila.view.eventView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class EventPanel extends JPanel {
    private int ID;

    public EventPanel(int ID) {
        this.ID = ID;
    }

    public void paintComponent(Graphics g) {
        int x = 0, y = 0;
        Random a = new Random(6);
        String imgURL;
        switch (ID) {
            case (1):
                imgURL = "src/image/event/shipbay.jpg";
                break;
            case (2):
                imgURL = "src/image/event/bore.jpg";
                break;
            case (3):
                imgURL = "src/image/event/disease.jpg";
                break;
            case (4):
                imgURL = "src/image/event/financialrisk.jpg";
                break;
            case (5):
                imgURL = "src/image/event/market.jpg";
                break;
            default:
                imgURL = "src/image/event/marine.jpg";
                break;
        }


        //test.jpg是测试图片，与Demo.java放在同一目录下
        ImageIcon icon = new ImageIcon(imgURL);
        icon.setImage(icon.getImage().getScaledInstance(400, 260, Image.SCALE_DEFAULT));
        g.drawImage(icon.getImage(), x, y, 300, 200, this);

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

