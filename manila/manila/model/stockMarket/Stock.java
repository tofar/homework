package manila.model.stockMarket;

public class Stock {
    /**
     * 股票价格上限
     */
    private final int UPPER_LIMIT_PRICE = 30;
    /**
     * 股票当前价格
     */
    private int currentPrice;
    /**
     * 股票对应的货物的id
     */
    private int cargoId;

    /**
     * 股票剩余张数
     */
    private int surplus;

    /**
     * 股票构造函数
     *
     * @param cargoId 股票对应的货物的id
     */
    public Stock(int cargoId) {
        this.currentPrice = 0;
        this.cargoId = cargoId;
        this.surplus = 4;

    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    /**
     * 股票价格上涨
     */
    public void upCurrentPrice() {
        switch (currentPrice) {
            case 0:
                currentPrice = 5;
                break;
            case 5:
                currentPrice = 10;
                break;
            case 10:
                currentPrice = 20;
                break;
            case 20:
                currentPrice = 30;
                break;
            default:
                break;
        }
    }

    public void downCurrentPrice() {
        switch (currentPrice) {
            case 5:
                currentPrice = 0;
                break;
            case 10:
                currentPrice = 5;
                break;
            case 20:
                currentPrice = 10;
                break;
            case 30:
                currentPrice = 20;
            default:
                break;
        }
    }

    /**
     * 判断是否达到当前股票上线
     */
    public boolean ifReachLimit() {
        return currentPrice >= UPPER_LIMIT_PRICE;
    }

    public int getSurplus() {
        return surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }

    /**
     * 减少股票数量
     *
     * @param n 数量
     */
    public void cutSurplus(int n) {
        this.surplus -= n;
    }
}
