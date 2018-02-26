package manila.model.position;

/**
 * 海盗湾
 */
public class PirateBay extends Position {
    /**
     * 位置级别 从1开始，1为最高级别
     */
    private int rank;

    /**
     * 海盗湾构造函数
     *
     * @param price 位置购买价格
     * @param rank  位置级别
     */
    public PirateBay(int price, int rank) {
        super(price);

        this.rank = rank;
    }
}
