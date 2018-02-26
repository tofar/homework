package manila.controller.moveBoatInAdvance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class DownController implements ActionListener {
    /**
     * 船的id号和位置
     */
    HashMap<Integer, Integer> record;
    private int boat_id;
    private JButton boatView;


    public DownController(JButton boatView, int boat_id) {
        this.boatView = boatView;
        this.boat_id = boat_id;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        downMove();
    }

    public void downMove() {
        if (record.containsKey(boat_id)) {
            if (record.get(boat_id) >= 1) {
                record.put(boat_id, record.get(boat_id) - 1);
                int x = boatView.getX();
                int y = boatView.getY();
                int width = boatView.getWidth();
                int height = boatView.getHeight();
                boatView.setBounds(x, y + 20, width, height);
            }
        }
    }

    public void setRecord(HashMap<Integer, Integer> record) {
        this.record = record;
    }
}
