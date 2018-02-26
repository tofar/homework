package manila.controller.moveBoatInAdvance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class UpController implements ActionListener {
    public JButton boatView;
    /**
     * 船的id号和位置
     */
    private HashMap<Integer, Integer> record;
    private int boat_id;

    public UpController(JButton boatView, int boat_id) {
        this.boatView = boatView;
        this.boat_id = boat_id;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        upMove();
    }

    public void upMove() {

        if (!ifReachThreeBoat() && record.containsKey(boat_id)) {
            if (record.get(boat_id) <= 4 && calTotal() < 9) {
                record.put(boat_id, record.get(boat_id) + 1);
                int x = boatView.getX();
                int y = boatView.getY();
                int width = boatView.getWidth();
                int height = boatView.getHeight();
                boatView.setBounds(x, y - 20, width, height);
            }
        }
    }

    public int calTotal() {
        int total = 0;
        for (int i : record.keySet()) {
            total += record.get(i);
        }
        return total;
    }

    public void setRecord(HashMap<Integer, Integer> record) {
        this.record = record;
    }

    public boolean ifReachThreeBoat() {
        int num = 0;
        for (int key : record.keySet()) {
            if (record.get(key) > 0) {
                num++;
            }
        }

        return num >= 3;
    }
}
