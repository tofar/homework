package manila.model.main;

import manila.model.stockMarket.Stock;

/**
 * 货物
 */
public class Cargo {
    /**
     * 利润
     */
    private int profit;
    /**
     * 货物id
     */
    private int cargoId;
    /**
     * 货物名字
     */
    private String cargoName;

    /**
     * 货物对应的股票
     */
    private Stock stock;

    /**
     * 海盗湾构造函数
     *
     * @param profit    货物利润
     * @param cargoId   货物Id
     * @param cargoName 货物名字
     */
    public Cargo(int profit, int cargoId, String cargoName) {
        this.profit = profit;
        this.cargoId = cargoId;
        this.cargoName = cargoName;
        this.stock = new Stock(this.cargoId);
    }

    public int getProfit() {
        return profit;
    }

    public int getCargoId() {
        return cargoId;
    }

    public String getCargoName() {
        return cargoName;
    }

    public Stock getStock() {
        return stock;
    }
}
