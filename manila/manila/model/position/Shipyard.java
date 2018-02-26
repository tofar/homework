package manila.model.position;

import manila.model.main.Boat;

/**
 * 修船厂
 */
public class Shipyard extends Position {
    /**
     * 该位置默认收入
     */
    private int profit;
    private Boat boat;

    /**
     * 位置级别 从1开始，1为最高级别
     */
    private int rank;

    /**
     * 修船厂构造函数
     *
     * @param profit 成功之后的利润
     * @param price  位置购买价格
     */
    public Shipyard(int profit, int price, int rank) {
        super(price);
        this.profit = profit;
        this.rank = rank;
    }

    public void setBoat(Boat boat) {
        boat.setIs_arrived(true);
        this.boat = boat;

        if (this.getPlayer() != null) {
            this.getPlayer().addAccount_balance(profit);
        }
    }

    public boolean isSpare() {
        return boat == null;
    }

    public int getProfit() {
        return profit;
    }
}
