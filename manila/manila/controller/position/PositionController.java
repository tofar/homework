package manila.controller.position;

import manila.model.main.Game;
import manila.view.main.PositionView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * 位置基础按钮事件监听 控制器
 */
public abstract class PositionController implements ActionListener {

    PositionView positionView;
    private Game game;


    public void actionPerformed(ActionEvent actionEvent) {
        // 主游戏环节中设置水手
        // 购买位置都需要付钱
        if (game.getGame_status() == 1) {
            if (positionView.getPosition().getPlayer() == null) {
                if (game.getCurrentPlayer().getAccount_balance() >= positionView.getPosition().getPrice()) {

                    positionView.getPosition().setPlayer(game.getCurrentPlayer());

                    positionView.getPosition().cutAccount_balance(positionView.getPosition().getPrice());

                    System.out.println(positionView.getPosition().getPlayer().getAccount_balance());

                    positionView.setSpare(false);

                    // TODO
                    positionView.setBorder(BorderFactory.createLineBorder(Color.RED));


                    game.setcurrentPlayerId((game.getcurrentPlayerId() + 1) % 3);
                    game.setSelectTime(game.getSelectTime() + 1);
                    if (game.getSelectTime() == 3) {
                        game.setSelectTime(0);
                        game.setGame_status(0);
                    }

                } else {
                    System.out.println("你的资金不够购买这个位置");
                }
            } else {
                System.out.println("这个位置已经有了水手");
            }
        }

    }

    public void setGame(Game game) {
        this.game = game;
    }
}
