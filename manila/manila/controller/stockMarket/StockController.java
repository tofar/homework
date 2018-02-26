package manila.controller.stockMarket;

import manila.model.stockMarket.StockMarket;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StockController implements MouseListener {


    private StockMarket stockMarket;
    private int stockId;
    private JLabel currentStock;

    public StockController(StockMarket stockMarket, JLabel currentStock, int stockId) {
        this.stockMarket = stockMarket;
        this.currentStock = currentStock;
        this.stockId = stockId;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        stockMarket.setCurrentStockId(stockId);
        switch (stockId) {
            case 1:
                currentStock.setText("丝绸");
                break;
            case 2:
                currentStock.setText("可可");
                break;
            case 3:
                currentStock.setText("玉器");
                break;
            case 4:
                currentStock.setText("水晶");
                break;
            default:
                break;
        }
    }


}
