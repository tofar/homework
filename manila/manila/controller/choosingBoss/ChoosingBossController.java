package manila.controller.choosingBoss;

import manila.model.main.Player;
import manila.view.choosingBoss.ChoosingBossView;
import manila.view.stockMarket.StockMarketView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO 贝贝

/**
 * Manila 游戏选举船老大的窗口控制器。
 */
public class ChoosingBossController implements ActionListener {

    /**
     * 选船老大的view
     */
    private ChoosingBossView cbv;
    /**
     * 输入金额
     */
    private int bid_amount;
    /**
     * boss id
     */
    private int boss_pid;

    /**
     * 选取船老大控制器构造函数
     *
     * @param cbv 选取船老大的View层
     */
    public ChoosingBossController(ChoosingBossView cbv) {
        this.cbv = cbv;
        this.bid_amount = 0;
    }

    /**
     * 当前玩家参与竞价，如果输入价格小于当前标价，也认为竞标有效，自动将标价+1
     * 如果输入为空，则不作相应（默认输入为数字，不处理其他复杂情况）。
     * 读取完输入后将文本框内容清空，并将当前玩家设为船老大。
     * 将下一名玩家设为当前玩家，并更新竞选面板的显示内容。
     */
    public void bid() {
        // 读取玩家输入的金额
        String amountText = this.cbv.getAmountT().getText();
        if (!amountText.equals("")) {
            int amount = Integer.parseInt(amountText);
            if (amount > this.bid_amount) {
                this.bid_amount = amount;
            } else {
                this.bid_amount++;
            }

            this.boss_pid = this.cbv.getGame().getcurrentPlayerId();
            this.cbv.getBossLabel().setText(this.cbv.getGame().getCurrentPlayer().getName()
                    + " " + this.bid_amount + "$");
            this.cbv.getAmountT().setText("");
            this.cbv.updateBidView(this.boss_pid, false);

            this.cbv.getGame().switchPlayer();
            this.cbv.updateBidView(this.cbv.getGame().getcurrentPlayerId(), true);
        } else
            System.out.println("请输入金额");

    }

    /**
     * 当前玩家跳过竞价环节，不加价。
     * 将下一名玩家设为当前玩家，并更新竞选面板的显示内容。
     */
    public void pass() {
        this.cbv.updateBidView(this.cbv.getGame().getcurrentPlayerId(), false);

        this.cbv.getGame().switchPlayer();
        this.cbv.updateBidView(this.cbv.getGame().getcurrentPlayerId(), true);
    }

    /**
     * 竞价结束，产生船老大(扣除费用)，
     * 将船老大设置为游戏的当前玩家，
     * 关闭竞选窗口，更新主游戏画面（扣除费用，并为船老大对应玩家加框）。
     */
    public void confirm() {
        Player p = this.cbv.getGame().getPlayerByID(boss_pid);
        p.setAccount_balance(p.getAccount_balance() - this.bid_amount);

        // 设置当前玩家
        this.cbv.getGame().setcurrentPlayerId(boss_pid);
        this.cbv.getGame().setbossPlayerId(boss_pid);

//        this.cbv.getGame().getGameV().updatePlayersView(0, false);

        // 修改余额的显示
//        this.cbv.getGame().getGameV().updatePlayersView(boss_pid, false);
        // 显示边框
//        this.cbv.getGame().getGameV().updatePlayersView(boss_pid, true);
//        this.cbv.setVisible(!this.cbv.isShowing());
        this.cbv.dispose();
        // 无人竞选直接跳过购买股票阶段
        if (bid_amount > 0) {
            StockMarketView stockMarketView = new StockMarketView(cbv.getGame().getStockMarket(), cbv.getGame().getBossPlayer(),
                    cbv.getGame().getGameV().getPlayground().getBoatViews(), cbv.getGame().getCargos());
            stockMarketView.setVisible(true);
        }

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if (arg0.getActionCommand().equals("bid")) {
            this.bid();
        } else if (arg0.getActionCommand().equals("pass")) {
            this.pass();
        } else if (arg0.getActionCommand().equals("confirm")) {
            this.confirm();
        }
    }

}
