package manila.model.position;

import manila.model.main.Player;

public abstract class Position {
    /**
     * 登上该位置所要支付的费用
     */
    private int price;
    /**
     * 登上该位置的水手所对应的玩家ID，为空时值为-1
     */
    // TODO 后期删除
    private int sailorID;
    private Player player;

    /**
     * 默认位置构造函数
     *
     * @param price 位置购买费用
     */
    public Position(int price) {
        this.price = price;
        this.sailorID = -1;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSailorID() {
        return sailorID;
    }

    public void setSailorID(int sailorID) {
        this.sailorID = sailorID;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addAccount_banlance(int account_balance) {
        player.addAccount_balance(account_balance);
    }

    public void cutAccount_balance(int account_balance) {
        player.cutAccount_balance(account_balance);
    }

    /**
     * 是否已经有人玩家买了这个位置
     */
    public boolean isSpare() {
        return player == null;
    }
}
