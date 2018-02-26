package manila.controller.main;

import manila.model.main.Boat;
import manila.model.main.Game;
import manila.model.main.Player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// TODO 小蓝

/**
 * 控制鼠标交互的事件监听类
 */
public class GameController implements MouseListener {

    private Game game;

    /**
     * 游戏控制器构造函数
     *
     * @param g 游戏类
     */
    public GameController(Game g) {
        this.game = g;
    }

    public void clickedOnBoat(int x, int y) {
        Boat[] boats = game.getBoats();
        for (int i = 0; i < boats.length; i++) {
            Boat b = boats[i];
            if (b.isCursorInside(x, y)) {
                if (b.getAvailPosIndex() != -1) {
                    Player p = this.game.getCurrentPlayer();
                    p.payPos(b.getAvailPosPrice());
                    b.getOnboard(p.getPid());

                    // modify the view
                    this.game.getGameV().getPlayground().repaint();
//                    this.game.getGameV().updatePlayersView(p.getPid(), false);


                    if (this.game.getcurrentPlayerId() == this.game.getbossPlayerId() - 1
                            || this.game.getcurrentPlayerId() == this.game.getPlayers().length + this.game.getbossPlayerId() - 1) {
                        this.game.setChoosing(false);
                    }

                    this.game.switchPlayer();
//                    this.game.getGameV().updatePlayersView(this.game.getcurrentPlayerId(), true);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
        if (!this.game.isGameIsOver() && this.game.isChoosing()) {
            this.clickedOnBoat(arg0.getX(), arg0.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }


}
