package manila.view.main;

import manila.model.position.Position;

import javax.swing.*;

//TODO 静雯

/**
 * 各种position的view, 如海盗湾，领航员岛，修船厂，港口，船上的座位，保险公司等
 */
public class PositionView extends JButton {
    private Position position;
    private boolean isSpare;

    public PositionView(Position position) {
        this.position = position;
        isSpare = true;
    }

    public Position getPosition() {
        return position;
    }

    public void setSpare(boolean spare) {
        isSpare = spare;
    }
}
