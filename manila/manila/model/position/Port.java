package manila.model.position;

import manila.model.main.Boat;

/**
 * 港口
 */
public class Port extends Position {
    /**
     * 利润
     */
    private int profit;
    private Boat boat;

    /**
     * 位置级别 从1开始，1为最高级别
     */
    private int rank;

    /**
     * 港口构造函数
     *
     * @param profit 利润
     * @param price  位置购买价值
     * @param rank   位置级别
     */
    public Port(int profit, int price, int rank) {
        super(price);
        this.profit = profit;
        this.rank = rank;
    }

    public int getProfit() {
        return profit;
    }

    /**
     * 船进港口 同时玩家收益
     *
     * @param boat
     */
    public void setBoat(Boat boat) {
        boat.setIs_arrived(true);
        this.boat = boat;

        if (this.getPlayer() != null) {
            System.out.println("the sailor: " + this.getPlayer().getName());
            this.getPlayer().addAccount_balance(profit);
        }
    }

    /**
     * 是否已经有船停靠
     */
    public boolean isSpare() {
        return boat == null;
    }
}
