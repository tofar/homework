package manila.view.stockMarket;

import manila.controller.stockMarket.StockController;
import manila.controller.stockMarket.StockMarketController;
import manila.model.main.Cargo;
import manila.model.main.Player;
import manila.model.stockMarket.StockMarket;
import manila.view.main.BoatView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

// TODO 曲奂  内容：船老大选择股票界面，自由设计

/**
 * 船老大选股票页面
 */
public class StockMarketView extends JFrame {

    private StockMarketController stockMarketController;

    /**
     * 股票市场
     */
    private StockMarket stockMarket;

    /**
     * 主面板
     */
    private JPanel contentPane;

    private JLabel currentStock;


    /**
     * 跳过本轮股票购买的按钮
     */
    private JButton passButton;
    /**
     * 确认购买的按钮
     */
    private JButton confirmButton;

    // TODO

    /**
     * 股票市场的View层构造函数
     *
     * @param stockMarket 股票市场
     */
    public StockMarketView(StockMarket stockMarket, Player boss, BoatView[] boatViews, Cargo[] cargos) {

        this.stockMarket = stockMarket;


//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("当前金币：");
        label.setBounds(47, 6, 80, 31);
        contentPane.add(label);

        StockMarketController stockMarketController = new StockMarketController(this, boatViews, boss, cargos);

        confirmButton = new JButton("确认");
        confirmButton.setBounds(84, 243, 100, 29);
        confirmButton.addActionListener(stockMarketController);
        confirmButton.setActionCommand("confirm");
        contentPane.add(confirmButton);

        passButton = new JButton("跳过");
        passButton.setBounds(264, 243, 92, 29);
        passButton.addActionListener(stockMarketController);
        passButton.setActionCommand("pass");
        contentPane.add(passButton);


        JLabel playAccount = new JLabel(String.valueOf(boss.getAccount_balance()));
        playAccount.setBounds(123, 6, 61, 31);
        contentPane.add(playAccount);

        JPanel panel = new JPanel();
        panel.setBounds(47, 36, 344, 195);
        contentPane.add(panel);
        panel.setLayout(null);

        currentStock = new JLabel("xxx");
        currentStock.setBounds(368, 6, 61, 31);
        contentPane.add(currentStock);

        StockController[] stockControllers = new StockController[4];
        stockControllers[0] = new StockController(stockMarket, currentStock, 1);
        stockControllers[1] = new StockController(stockMarket, currentStock, 2);
        stockControllers[2] = new StockController(stockMarket, currentStock, 3);
        stockControllers[3] = new StockController(stockMarket, currentStock, 4);

        JLabel lblA = new JLabel("丝绸");
        lblA.setBounds(20, 62, 46, 127);
        lblA.addMouseListener(stockControllers[0]);
        panel.add(lblA);

        JLabel lblB = new JLabel("可可");
        lblB.setBounds(105, 62, 46, 127);
        lblB.addMouseListener(stockControllers[1]);
        panel.add(lblB);

        JLabel lblC = new JLabel("玉器");
        lblC.setBounds(192, 62, 46, 127);
        lblC.addMouseListener(stockControllers[2]);
        panel.add(lblC);

        JLabel lblD = new JLabel("水晶");
        lblD.setBounds(277, 62, 46, 127);
        lblD.addMouseListener(stockControllers[3]);
        panel.add(lblD);

        JLabel lblPrice_1 = new JLabel(" " + String.valueOf(stockMarket.getStocks().get(0).getCurrentPrice()) + " ");
        lblPrice_1.setBounds(20, 19, 46, 16);
        panel.add(lblPrice_1);

        JLabel lblPrice_2 = new JLabel(" " + String.valueOf(stockMarket.getStocks().get(1).getCurrentPrice()) + " ");
        lblPrice_2.setBounds(105, 19, 46, 16);
        panel.add(lblPrice_2);

        JLabel lblPrice_3 = new JLabel(" " + String.valueOf(stockMarket.getStocks().get(2).getCurrentPrice()) + " ");
        lblPrice_3.setBounds(192, 19, 46, 16);
        panel.add(lblPrice_3);

        JLabel lblPrice_4 = new JLabel(" " + String.valueOf(stockMarket.getStocks().get(3).getCurrentPrice()) + " ");
        lblPrice_4.setBounds(277, 19, 46, 16);
        panel.add(lblPrice_4);

        JLabel label_1 = new JLabel("选择股票：");
        label_1.setBounds(292, 6, 80, 31);
        contentPane.add(label_1);

    }

    public StockMarket getStockMarket() {
        return stockMarket;
    }

    public void setStockMarket(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
    }

    public StockMarketController getStockMarketController() {
        return stockMarketController;
    }

    public void setStockMarketController(StockMarketController stockMarketController) {
        this.stockMarketController = stockMarketController;
    }

    public JLabel getCurrentStock() {
        return currentStock;
    }

}
