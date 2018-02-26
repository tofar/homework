package manila.model.position;

/**
 * 领航员岛
 */
public class IslandOfNavigator extends Position {
    /**
     * 位置级别 从1开始，1为最高级别
     */
    private int rank;
    /**
     * 状态 0 为休息状态， 1为工作状态
     */
    private int status;

    /**
     * 领航员岛构造函数
     *
     * @param price 位置购买价格
     * @param rank  位置级别
     */
    public IslandOfNavigator(int price, int rank) {
        super(price);
        this.rank = rank;
        this.status = 0;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
