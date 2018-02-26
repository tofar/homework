package manila.view.main;

import manila.controller.main.DiceController;
import manila.model.main.Boat;
import manila.model.main.Game;
import manila.model.main.Player;
import manila.view.eventView.PirateOnBoatView;

import javax.swing.*;
import java.awt.*;

//TODO 静雯

/**
 * 游戏主界面，包含main函数
 */
public class GameView extends JPanel {
    /**
     * 信息窗口的宽度
     */
    private static final int INFO_W = 300;
    /**
     * 信息窗口的高度
     */
    private static final int INFO_H = 800;
    /**
     * 确认按钮
     */
    JButton confirmButton;
    /**
     * 抵押股票按钮
     */
    JButton mortgageButton;
    /**
     * 放弃按钮
     */
    JButton passButton;
    private Game game;
    /**
     * 游戏场景窗口
     */
    private BoardView playground;
    /**
     * 信息窗口
     */
    private JPanel infoView;
    /**
     * 玩家信息窗口
     */
    // TODO 以后修改为PlayView
    private JPanel playersView;
    /**
     * 摇骰子的窗口
     */
    private JPanel diceView;
    /**
     * 存放玩家信息视图的数组
     */
    private PlayerView[] playersV;
    /**
     * 控制摇骰子的按钮
     */
    private JButton diceButton;

    public GameView() {
        this.game = new Game(this);

        this.playground = new BoardView(this.game);
//        this.infoView = new JPanel();

//        this.makePlayerView();
//        this.makeDiceView();

//        this.infoView.setPreferredSize(new Dimension(INFO_W, INFO_H));
//        this.infoView.setBackground(Color.GREEN);
//        this.infoView.setLayout(new BorderLayout());
//        this.infoView.add(playersView, BorderLayout.CENTER);
//        this.infoView.add(diceView, BorderLayout.SOUTH);

        this.add(this.playground);
//        this.add(this.infoView);


    }


    /**
     * 对玩家信息视图进行初始化
     */
    public void makePlayerView() {
        this.playersView = new JPanel();
        this.playersView.setLayout(new GridLayout(4, 1));
        this.playersView.setPreferredSize(new Dimension(INFO_W, 300));

        JLabel text = new JLabel("玩家信息");
        text.setHorizontalTextPosition(SwingConstants.LEFT);
        text.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 24));
        this.playersView.add(text);
        Player[] players = this.game.getPlayers();
        this.playersV = new PlayerView[players.length];
        for (int i = 0; i < players.length; i++) {
            PlayerView pv = new PlayerView(players[i], true);
            if (this.game.getcurrentPlayerId() == players[i].getPid()) {
                pv.setActive(true);
            }
            this.playersV[i] = pv;
            this.playersView.add(pv);
        }
    }

    /**
     * 对摇骰子的视图进行初始化
     */
    public void makeDiceView() {
        this.diceView = new JPanel();
        this.diceView.setPreferredSize(new Dimension(INFO_W, 500));
        this.diceView.setBackground(Color.GRAY);
        this.diceButton = new JButton("前进");
        this.diceButton.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 24));
        this.diceButton.addActionListener(new DiceController(this.game));
        this.diceView.add(this.diceButton);
    }
//
//    /**
//     * 对玩家的显示在界面上的相关信息进行更新
//     *
//     * @param pid    对应玩家的ID
//     * @param active 是否标记此玩家为当前玩家
//     */
//    public void updatePlayersView(int pid, boolean active) {
//        for (PlayerView pv : this.playersV) {
//            Player p = pv.getPlayer();
//            if (p.getPid() == pid) {
//                if (!active) {
//                    pv.getScoreV().setText(p.getAccount_balance() + "$");
//                    pv.getWorker_nbV().setText(p.getWorker_nb() + "");
//                }
//                pv.setActive(active);
//            }
//
//        }
//    }

    public BoardView getPlayground() {
        return playground;
    }

    public void setPlayground(BoardView playground) {
        this.playground = playground;
    }

//    public JPanel getPlayersView() {
//        return playersView;
//    }

    public void setPlayersView(JPanel playersView) {
        this.playersView = playersView;
    }

    public PlayerView[] getPlayersV() {
        return playersV;
    }

    public void setPlayersV(PlayerView[] playersV) {
        this.playersV = playersV;
    }


    public Game getGame() {
        return game;
    }

    // TODO
    public void meetPirate() {
        // 由于只有一个海盗
        for (Boat boat : game.getBoats()) {
            if (boat.getPos_in_the_sea() == 13) {
                game.getBoard().getPirateBays().get(0).addAccount_banlance(boat.getCargo_value());
                System.out.println("pirate " + game.getBoard().getPirateBays().get(0).getPlayer().getName()
                        + "get value: " + boat.getCargo_value());
                System.out.println("Sorry, you meet pirate.");

                PirateOnBoatView pirateOnBoatView = new PirateOnBoatView();
                pirateOnBoatView.setVisible(true);
            }
        }


    }

    // TODO
    public void islandOfNavigatorTime() {
        System.out.println("Now it is time for islandOfNavigator.");
    }

}
