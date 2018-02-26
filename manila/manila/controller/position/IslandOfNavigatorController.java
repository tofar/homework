package manila.controller.position;

import manila.view.main.PositionView;

import java.awt.event.ActionEvent;


/**
 * 领航员岛按钮事件监听 控制器
 */
public class IslandOfNavigatorController extends PositionController {

    public IslandOfNavigatorController(PositionView positionView) {
        this.positionView = positionView;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        super.actionPerformed(actionEvent);
    }


}
