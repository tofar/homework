package manila.controller.main;

import manila.model.main.Boat;
import manila.model.main.Game;
import manila.model.main.Player;
import manila.model.position.Shipyard;
import manila.view.eventView.ExceptionEventView;
import manila.view.main.BoatView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// TODO 小蓝

/**
 * 控制摇骰子的按钮的事件监听类
 */
public class DiceController implements ActionListener {

    // 鉴于 老师的game里面有GameView，我懒得改了
    private Game game;

    /**
     * 选取骰子控制器构造函数
     *
     * @param g 游戏类
     */
    public DiceController(Game g) {
        this.game = g;
    }


    @Override
    public void actionPerformed(ActionEvent arg0) {

        game.setGame_status(0);
        if (game.getCurrent_round() <= Game.SEA_LENGTH) {
            if (game.getCurrent_round() == 1) {
                // 随机事件发生。。。
                randomEvent();
            }
            // 领航员功能模块
            islandOfNavigatorFunc();

            // TODO Auto-generated method stub
            // roll the dice to move the boats

            // TODO View加上船超过线之后消失
//        if (!this.game.isGameIsOver() && !this.game.isChoosing()) {
            for (Boat b : this.game.getBoats()) {
                b.move(this.game.rollDice());
            }
            this.game.getGameV().getPlayground().moveBoats();

            // prepare the next round
            this.game.setCurrent_round(this.game.getCurrent_round() + 1);
            this.game.setChoosing(true);

            // 顺序不能错
            upStockPrice();
            portFunc();


            if (this.game.getCurrent_round() == Game.ROUND_NUMBER) {

                pirateBayFunc();
                game.calculateProfits();

                shipyardFunc();
                insuranceCompanyFunc();

                if (game.ifGameOver()) {
                    this.game.setGameIsOver(true);

                    // TODO 里面计算总价值，包含总价值展示等
                    this.game.showWinner();
                } else {
                    this.game.nestRound();
//                    try {
//                        this.game.nestRound();
//                        Thread.sleep(1000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }

            }
//            game.getGameV().getPlayersView().repaint();
//        }
            game.setGame_status(1);

        }

    }


    public void islandOfNavigatorFunc() {
        if (game.ifExistIslandOfNavigator()) {
            game.setGame_status(2);

            for (BoatView boatView : game.getGameV().getPlayground().getBoatViews()) {
                boatView.setStatus(0);
            }
            game.getGameV().islandOfNavigatorTime();
        }
    }


    public void pirateBayFunc() {
        if (game.ifExistPirate()) {
            game.setGame_status(3);
            game.getGameV().meetPirate();
        }
    }

    public void portFunc() {
        for (BoatView boatView : game.getGameV().getPlayground().getBoatViews()) {
            if (boatView.getBoat().getIs_arrived() && boatView.getBoat().getPos_in_the_sea() > Game.SEA_LENGTH) {
                int port_profit = game.getBoard().getFirstSparePort().getProfit();

                switch (port_profit) {
                    case 6:
                        changeColor(game.getGameV().getPlayground().getBoatA());
                        break;
                    case 8:
                        changeColor(game.getGameV().getPlayground().getBoatB());
                        break;
                    case 15:
                        changeColor(game.getGameV().getPlayground().getBoatC());
                        break;
                    default:
                        break;

                }

                game.getBoard().getFirstSparePort().setBoat(boatView.getBoat());
                boatView.setVisible(false);
                System.out.println("arrive in the Port.");

            }
        }

        //TODO 直接设置信息就行，不需要repaint
//        game.getGameV().getPlayersView().repaint();

    }


    public void shipyardFunc() {
        for (BoatView boatView : game.getGameV().getPlayground().getBoatViews()) {
            if (!boatView.getBoat().getIs_arrived() && boatView.getBoat().getPos_in_the_sea() < Game.SEA_LENGTH) {
                int port_profit = game.getBoard().getFirstSpareShipyard().getProfit();
                switch (port_profit) {
                    case 6:
                        changeColor(game.getGameV().getPlayground().getBrokenBoatA());
                        break;
                    case 8:
                        changeColor(game.getGameV().getPlayground().getBrokenBoatB());
                        break;
                    case 15:
                        changeColor(game.getGameV().getPlayground().getBrokenBoatC());
                        break;
                    default:
                        break;

                }

                game.getBoard().getFirstSpareShipyard().setBoat(boatView.getBoat());
                System.out.println("船沉了");

                boatView.getBoat().setPos_in_the_sea(0);
            }
        }

    }

    public void insuranceCompanyFunc() {
        for (Shipyard shipyard : game.getBoard().getShipyards()) {
            if (!shipyard.isSpare()) {
                game.getBoard().getInsuranceCompany().pay(shipyard.getProfit());
            }
        }
    }

    public void upStockPrice() {
        for (BoatView boatView : game.getGameV().getPlayground().getBoatViews()) {
            if (boatView.getBoat().getAvailPosIndex() >= Game.SEA_LENGTH && !boatView.getBoat().getIs_arrived()) {
                game.getStockMarket().getStockByCargoId(boatView.getBoat().getCargo().getCargoId()).upCurrentPrice();

                // TODO boatView消失，并复原Boat类的初始状态
                boatView.getBoat().setPos_in_the_sea(0);
                boatView.setVisible(false);

            }
        }
    }

    public void changeColor(JButton jButton) {
        jButton.setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    // TODO 随机事件
    public void randomEvent() {
        Random random = new Random();
        int i = random.nextInt(6) + 1;
        ExceptionEventView exceptionEventView = new ExceptionEventView(i);
        exceptionEventView.setVisible(true);
        switch (i) {
            case 1:
                eventOne();
                break;
            case 2:
                eventTwo();
                break;
            case 3:
                eventThree();
                break;
            case 4:
                eventFour();
                break;
            case 5:
                eventFive();
                break;
            case 6:
                eventSix();
                break;
        }

    }

    public void eventOne() {
        System.out.println("所有玩家增加20 披索");
        for (Player player: game.getPlayers()) {
            player.addAccount_balance(20);
            System.out.println(player.getName() + " account: " + String.valueOf(player.getAccount_balance()));
        }
    }

    public void eventTwo() {
        System.out.println("菲律宾南部的苏比克湾发生了大海啸！ \n     本轮所有船只退回原点！");
        for (Boat boat: game.getBoats()) {
            boat.setPos_in_the_sea(0);
        }
        game.getGameV().getPlayground().moveBoats();
    }

    public void eventThree() {

        for (Boat boat: game.getBoats()) {
            if (boat.getAvailPosIndex() != 0) {
                boat.getPos_list()[boat.getAvailPosIndex() - 1].setPlayer(null);
                boat.getPos_list()[boat.getAvailPosIndex() - 1].setSailorID(-1);
            }
        }
    }

    public void eventFour() {
        Random random = new Random();
        int n = random.nextInt(4 ) + 1;
        game.getStockMarket().getStockByCargoId(n).downCurrentPrice();
        n = random.nextInt(4) + 1;
        game.getStockMarket().getStockByCargoId(n).downCurrentPrice();

    }

    public void eventFive() {

    }

    public void eventSix() {

    }




}
