package manila.controller.position;

import manila.view.main.PositionView;

import java.awt.event.ActionEvent;

/**
 * 海盗湾按钮事件监听 控制器
 */
public class PirateBayController extends PositionController {


    public PirateBayController(PositionView positionView) {
        this.positionView = positionView;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        super.actionPerformed(actionEvent);
    }
}
