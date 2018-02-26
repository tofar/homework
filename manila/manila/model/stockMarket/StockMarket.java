package manila.model.stockMarket;

import manila.model.main.Player;

import java.util.ArrayList;

/**
 * 股票市场
 */
public class StockMarket {
    /**
     * 股票
     */
    private ArrayList<Stock> stocks;

    /**
     * 船老大
     */
    private Player captain;

    /**
     * 市场交易当前股票的id,即货物id, 没选则为 -1
     */
    private int currentStockId;

    public StockMarket() {
        stocks = new ArrayList<>();
        this.currentStockId = -1;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    /**
     * 通过货物Id获取股票
     */
    public Stock getStockByCargoId(int cargoId) {
        for (Stock stock : stocks) {
            if (stock.getCargoId() == cargoId) {
                return stock;
            }
        }

        return null;
    }

    public Player getCaptain() {
        return captain;
    }

    public void setCaptain(Player captain) {
        this.captain = captain;
    }

    public int getCurrentStockId() {
        return currentStockId;
    }

    public void setCurrentStockId(int currentStockId) {
        this.currentStockId = currentStockId;
    }

    public int getNumberOfStock() {
        return stocks.size();
    }

    /**
     * 判断是否有股票达到上限
     */
    public boolean ifOneStockReachLimit() {
        for (Stock stock : stocks) {
            if (stock.ifReachLimit()) {
                return true;
            }
        }

        return false;
    }


}
