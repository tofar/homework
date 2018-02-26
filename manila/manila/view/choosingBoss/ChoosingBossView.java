package manila.view.choosingBoss;

import manila.controller.choosingBoss.ChoosingBossController;
import manila.model.main.Game;
import manila.model.main.Player;
import manila.view.main.PlayerView;

import javax.swing.*;
import java.awt.*;

//TODO 曲奂

/**
 * Manila 游戏选举船老大的窗口。
 */
public class ChoosingBossView extends JFrame {
    private Game game;
    private ChoosingBossController choosingBossController;

    /**
     * 主面板
     */
    private JPanel content;
    /**
     * 玩家名称面板（用来装简略版PlayerView）
     */
    private JPanel playerView;
    /**
     * 当前船老大姓名和对应金额面板
     */
    private JPanel bossView;
    /**
     * 选择船老大的面板
     */
    private JPanel chooseView;
    /**
     * 用于显示当前船老大姓名和对应金额
     */
    private JLabel bossLabel;

    /**
     * 竞选金额输入框
     */
    private JTextField amountT;
    /**
     * 竞选按钮
     */
    private JButton bidButton;
    /**
     * 跳过本轮竞选按钮
     */
    private JButton passButton;
    /**
     * 结束竞选按钮
     */
    private JButton confirmButton;

    /**
     * 简略版PlayerView的数组
     */
    private PlayerView[] pvList;

    public ChoosingBossView(Game game) {
        this.game = game;
        this.choosingBossController = new ChoosingBossController(this);

        this.content = new JPanel();
        this.playerView = new JPanel();
        this.bossView = new JPanel();
        this.chooseView = new JPanel();

        this.setContentPane(this.content);
        this.content.setPreferredSize(new Dimension(400, 200));

        this.content.setLayout(new BorderLayout());
        this.pvList = new PlayerView[this.game.getPlayers().length];
        for (int i = 0; i < this.game.getPlayers().length; i++) {
            this.pvList[i] = new PlayerView(this.game.getPlayers()[i], false);
            this.playerView.add(this.pvList[i]);
            if (i == 0) {
                this.pvList[i].setActive(true);
            }
        }
        this.content.add(this.playerView, BorderLayout.NORTH);

        JLabel label = new JLabel("船老大:");
        label.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 24));
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        this.bossView.add(label);

        this.bossLabel = new JLabel("xxxx");
        this.bossLabel.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 24));
        this.bossView.add(bossLabel);
        this.content.add(this.bossView, BorderLayout.CENTER);

        this.amountT = new JTextField(10);
        this.bidButton = new JButton("竞选");
        this.passButton = new JButton("跳过");
        this.confirmButton = new JButton("确认");

        this.bidButton.addActionListener(this.choosingBossController);
        this.bidButton.setActionCommand("bid");
        this.passButton.addActionListener(this.choosingBossController);
        this.passButton.setActionCommand("pass");
        this.confirmButton.addActionListener(this.choosingBossController);
        this.confirmButton.setActionCommand("confirm");

        this.chooseView.add(this.amountT);
        this.chooseView.add(this.bidButton);
        this.chooseView.add(this.passButton);
        this.chooseView.add(this.confirmButton);

        this.content.add(this.chooseView, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setTitle("开始竞选吧");
        this.pack();
        this.setVisible(true);
    }

    public void updateBidView(int pid, boolean active) {
        for (PlayerView pv : this.pvList) {
            Player p = pv.getPlayer();
            if (p.getPid() == pid) {
                pv.setActive(active);
            }

        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public JTextField getAmountT() {
        return amountT;
    }

    public void setAmountT(JTextField amountT) {
        this.amountT = amountT;
    }

    public PlayerView[] getPvList() {
        return pvList;
    }

    public void setPvList(PlayerView[] pvList) {
        this.pvList = pvList;
    }

    public JLabel getBossLabel() {
        return bossLabel;
    }

    public void setBossLabel(JLabel bossLabel) {
        this.bossLabel = bossLabel;
    }


}
