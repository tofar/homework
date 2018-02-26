package manila.controller.moveBoatInAdvance;

import manila.model.main.Cargo;
import manila.view.advancedBoatMove.AdvancedBoatMove;
import manila.view.main.BoatView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class BasicController implements ActionListener {
    /**
     * 记录船的编号和移动步数
     */
    private HashMap<Integer, Integer> record;
    private AdvancedBoatMove advancedBoatMove;

    public BasicController(AdvancedBoatMove advancedBoatMove) {
        this.advancedBoatMove = advancedBoatMove;
        record = new HashMap<>();
        record.put(0, 0);
        record.put(1, 0);
        record.put(2, 0);
        record.put(3, 0);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("confirm")) {
            confirm();
        } else {
            if (actionEvent.getActionCommand().equals("pass")) {
                passMove();
            }
        }
    }

    public void confirm() {

        BoatView[] boatViews = advancedBoatMove.getBoatViews();
        ArrayList<Integer> threeBoatNum = getAvailThreeBoatNum();
        Cargo[] cargos = advancedBoatMove.getCargos();
        for (int i = 0; i < boatViews.length; i++) {
            boatViews[i].getBoat().setPos_in_the_sea(record.get(threeBoatNum.get(i)));
            boatViews[i].getBoat().setCargo(cargos[threeBoatNum.get(i)]);
            boatViews[i].getCargoView().setText(cargos[threeBoatNum.get(i)].getCargoName());

            int x = boatViews[i].getX();
            int y = boatViews[i].getY();
            int width = boatViews[i].getWidth();
            int height = boatViews[i].getHeight();
            boatViews[i].setBounds(x, y - 31 * record.get(i), width, height);
            boatViews[i].getCargoView().setText(boatViews[i].getBoat().getCargo_name());


        }


        advancedBoatMove.dispose();
    }

    public void passMove() {

    }

    public HashMap<Integer, Integer> getRecord() {
        return record;
    }

    /**
     * 得到可见的三个船的id
     *
     * @return
     */
    public ArrayList<Integer> getAvailThreeBoatNum() {
        ArrayList<Integer> threeBoatNum = new ArrayList<>();
        for (int i : record.keySet()) {
            if (record.get(i) != 0) {
                threeBoatNum.add(i);
            }
        }
        if (threeBoatNum.size() < 3) {
            for (int i : record.keySet()) {
                if (record.get(i) == 0) {
                    threeBoatNum.add(i);
                    if (threeBoatNum.size() == 3) {
                        break;
                    }
                }
            }
        }

        return threeBoatNum;
    }
}
