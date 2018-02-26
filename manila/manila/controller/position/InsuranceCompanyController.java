package manila.controller.position;

import manila.model.position.InsuranceCompany;
import manila.view.main.PositionView;

import java.awt.event.ActionEvent;


/**
 * 保险公司按钮监听 控制器
 */
public class InsuranceCompanyController extends PositionController {

    public InsuranceCompanyController(PositionView positionView) {
        this.positionView = positionView;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        super.actionPerformed(actionEvent);
        function();
    }

    /*
    保险公司立即获得 10 披索
     */
    public void function() {
        positionView.getPosition().addAccount_banlance(((InsuranceCompany) positionView.getPosition()).getProfit());
    }
}
