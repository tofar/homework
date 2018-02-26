package manila.controller.moveBoatInAdvance;

import manila.model.main.Cargo;
import manila.view.main.BoatView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CargoController implements ActionListener {

    public Cargo cargo;
    // 判断是否已经被点中
    public boolean status;
    public BoatView boatView;

    public CargoController(Cargo cargo) {
        this.cargo = cargo;
        this.status = false;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (status) {
            status = true;
            boatView.setVisible(false);

        } else {
            // TODO 提前新建好，或者就三个货物，就不用切换来切换去了
        }
    }
}
