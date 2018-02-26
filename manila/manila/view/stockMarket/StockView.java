package manila.view.stockMarket;

import manila.model.stockMarket.Stock;

import javax.swing.*;

//TODO 曲奂

/**
 * 股票区域view
 */
public class StockView extends JPanel {
    private Stock stock;

    /**
     * 股票的View层构造函数
     *
     * @param stock 股票
     */
    public StockView(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }


}
