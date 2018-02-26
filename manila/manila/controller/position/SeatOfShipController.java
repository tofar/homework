package manila.controller.position;

import manila.view.main.PositionView;

import java.awt.event.ActionEvent;


/**
 * 船上的座位按钮的事件监听 控制器
 */
public class SeatOfShipController extends PositionController {

    public SeatOfShipController(PositionView positionView) {
        this.positionView = positionView;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        super.actionPerformed(actionEvent);
    }
}
