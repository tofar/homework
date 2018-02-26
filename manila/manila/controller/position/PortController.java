package manila.controller.position;

import manila.view.main.PositionView;

import java.awt.event.ActionEvent;

/**
 * 港口按钮事件监听 控制器
 */
public class PortController extends PositionController {

    public PortController(PositionView positionView) {
        this.positionView = positionView;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        super.actionPerformed(actionEvent);
    }


}
