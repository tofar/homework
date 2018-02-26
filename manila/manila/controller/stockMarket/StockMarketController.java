package manila.controller.stockMarket;

import manila.model.main.Cargo;
import manila.model.main.Player;
import manila.view.advancedBoatMove.AdvancedBoatMove;
import manila.view.main.BoatView;
import manila.view.stockMarket.StockMarketView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO 贝贝

/**
 * 股票市场交易事件监听 控制器
 */
public class StockMarketController implements ActionListener {

    private StockMarketView stockMarketView;
    private Player boss;
    private BoatView[] boatViews;
    private Cargo[] cargos;

    /**
     * 股票市场交易控制器构造函数
     */
    public StockMarketController(StockMarketView stockMarketView, BoatView[] boatViews, Player boss, Cargo[] cargos) {
        this.stockMarketView = stockMarketView;
        this.boatViews = boatViews;
        this.boss = boss;
        this.cargos = cargos;
    }

    /**
     * 通过command
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // TODO Auto-generated method stub

        if (actionEvent.getActionCommand().equals("confirm")) {
            confirm();
        } else if (actionEvent.getActionCommand().equals("pass")) {
            pass();
        }
    }

    /**
     * 确认按钮时间响应
     */
    public void confirm() {
        if (stockMarketView.getStockMarket().getCurrentStockId() != -1) {
            boss.addStock(stockMarketView.getStockMarket().getStockByCargoId(stockMarketView.getStockMarket().getCurrentStockId()));
            System.out.println(boss.getName() + " account: " + boss.getAccount_balance());
        } else {
            System.out.println("你没有选择股票.");
        }

        stockMarketView.getStockMarket().setCurrentStockId(-1);
        stockMarketView.dispose();

        AdvancedBoatMove advancedBoatMove = new AdvancedBoatMove(boatViews, cargos);
        advancedBoatMove.setVisible(true);
    }

    public void pass() {
        stockMarketView.getStockMarket().setCurrentStockId(-1);
        stockMarketView.dispose();
        AdvancedBoatMove advancedBoatMove = new AdvancedBoatMove(boatViews, cargos);
        advancedBoatMove.setVisible(true);
    }

    public void setBoss(Player boss) {
        this.boss = boss;
    }
}
