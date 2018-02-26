package manila.model.main;

import manila.model.position.*;
import manila.model.stockMarket.Stock;
import manila.model.stockMarket.StockMarket;
import manila.view.choosingBoss.ChoosingBossView;
import manila.view.main.BoatView;
import manila.view.main.GameView;
import manila.view.main.PositionView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Manila 游戏的主要类，包含玩家信息和每条船的信息。
 */
public class Game {
    /**
     * 游戏的总轮数
     */
    public static final int ROUND_NUMBER = 3;
    /**
     * 海路的总长度
     */
    public static final int SEA_LENGTH = 13;
    /**
     * 玩家数组
     */
    private Player[] players;
    /**
     * 游戏面板容器
     */
    private Board board;
    /**
     * 股票市场
     */
    private StockMarket stockMarket;
    /**
     * 货物数组
     */
    private Cargo[] cargos;
    /**
     * 随机数产生器
     */
    private Random dice_generator;
    /**
     * 当前是否处于玩家选位置的阶段
     */
    private boolean choosing;
    /**
     * 游戏是否已结束
     */
    private boolean gameIsOver;
    /**
     * 当前游戏处于第几轮
     */
    private int current_round;
    /**
     * 当前正在选位置的玩家ID
     */
    private int currentPlayerId;
    /**
     * 被选为船老大的玩家ID
     */
    private int bossPlayerId;
    private GameView gameV;

    /**
     * 游戏状态，其中 1代表选位置的状态，2代表领航员时间，3代表海盗时间, 0为其他, 4代表提前移动环节
     */
    private int game_status;

    private int selectTime;

    /**
     * 游戏类构造函数
     *
     * @param gv Game的View层
     */
    public Game(GameView gv) {

        this.gameV = gv;

        game_status = 1;
        selectTime = 0;

        // 船的座位的初始化
        int[] prices1 = {3, 4, 5, 6};
        int[] prices2 = {2, 3, 4};
        int[] prices3 = {1, 2, 3};
        SeatOfShip[] pos1 = new SeatOfShip[prices1.length];
        SeatOfShip[] pos2 = new SeatOfShip[prices2.length];
        SeatOfShip[] pos3 = new SeatOfShip[prices3.length];

        for (int i = 0; i < prices1.length; i++) {
            pos1[i] = new SeatOfShip(prices1[i]);
        }
        for (int i = 0; i < prices2.length; i++) {
            pos2[i] = new SeatOfShip(prices2[i]);
        }
        for (int i = 0; i < prices3.length; i++) {
            pos3[i] = new SeatOfShip(prices3[i]);
        }

        // 初始化船
        Boat[] boats = new Boat[3];

        // TODO
        // 初始化货物，未来有选择货物的选项
        cargos = new Cargo[4];
        cargos[0] = new Cargo(36, 1, "可可");
        cargos[1] = new Cargo(18, 2, "坚果");
        cargos[2] = new Cargo(30, 3, "丝绸");
        cargos[3] = new Cargo(24, 4, "翡翠");

        boats[0] = new Boat(cargos[0], pos1);
        boats[1] = new Boat(cargos[1], pos2);
        boats[2] = new Boat(cargos[2], pos3);

        // 初始化股票市场
        this.stockMarket = new StockMarket();
        stockMarket.addStock(cargos[0].getStock());
        stockMarket.addStock(cargos[1].getStock());
        stockMarket.addStock(cargos[2].getStock());
        stockMarket.addStock(cargos[3].getStock());

        // 初始化各组件Position
        Position[] positions = new Position[11];

        // 顺时针顺序
        // 领航员岛
        positions[0] = new IslandOfNavigator(5, 1);
        positions[1] = new IslandOfNavigator(2, 2);
        // 海盗湾
        positions[2] = new PirateBay(5, 1);
        positions[3] = new PirateBay(5, 2);
        // 港口
        positions[4] = new Port(6, 4, 1);
        positions[5] = new Port(8, 3, 2);
        positions[6] = new Port(15, 2, 3);
        // 修船厂
        positions[7] = new Shipyard(6, 4, 1);
        positions[8] = new Shipyard(8, 3, 2);
        positions[9] = new Shipyard(15, 2, 3);
        // 保险公司
        positions[10] = new InsuranceCompany(10, 0);

        this.board = new Board(SEA_LENGTH, positions, boats);

        this.dice_generator = new Random();
        this.currentPlayerId = 0;
        this.bossPlayerId = 0;
        this.current_round = 0;
        this.choosing = true;
        this.gameIsOver = false;

        this.players = new Player[3];
        this.players[0] = new Player("路飞", 0, Color.RED);
        this.players[1] = new Player("杰克", 1, Color.GREEN);
        this.players[2] = new Player("哥伦布", 2, Color.BLUE);

        for (Player player : players) {
            Random random = new Random();
            for (int i = 0; i < 2; i++) {
                int n = random.nextInt(4) + 1;
                if (player.getStocksMap().containsKey(stockMarket.getStockByCargoId(n))) {
                    player.getStocksMap().put(stockMarket.getStockByCargoId(n), 2);
                } else {
                    player.getStocksMap().put(stockMarket.getStockByCargoId(n), 1);
                }
                stockMarket.getStockByCargoId(n).cutSurplus(1);
            }
        }
    }


    public void nestRound() {
        // 通过 setVisible()来显示、隐藏组件来写回合。每轮需要保存的信息只有玩家信息和股票市场信息
        // 方法1： 记录玩家信息和股票信息之后重启界面
        // 方法2： 设置显示，隐藏

        // 方法2
        // TODO
        game_status = 0;
        this.currentPlayerId = 0;
        this.bossPlayerId = -1;
        this.current_round = 0;
        this.choosing = true;
        this.gameIsOver = false;

        // 船 -> 货物 -> 位置
        for (Boat boat : board.getBoats()) {
            boat.setPos_in_the_sea(0);
            boat.setIs_arrived(false);
            for (SeatOfShip seatOfShip : boat.getPos_list()) {
                seatOfShip.setPlayer(null);
                seatOfShip.setSailorID(-1);
            }

            // TODO cargo
        }

        for (Position position : board.getPositions()) {
            position.setPlayer(null);
            position.setSailorID(-1);
        }

        for (BoatView boatView : gameV.getPlayground().getBoatViews()) {
            for (PositionView positionView : boatView.getSeatViews()) {
                positionView.setBorder(BorderFactory.createLineBorder(Color.darkGray));
            }
        }

        gameV.getPlayground().moveBoats();

        for (PositionView positionView : gameV.getPlayground().getPositionViews()) {
            positionView.getPosition().setPlayer(null);
            positionView.getPosition().setSailorID(-1);
            positionView.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        }

        gameV.getPlayground().getBoatA().setBorder(BorderFactory.createLineBorder(Color.darkGray));
        gameV.getPlayground().getBoatB().setBorder(BorderFactory.createLineBorder(Color.darkGray));
        gameV.getPlayground().getBoatC().setBorder(BorderFactory.createLineBorder(Color.darkGray));
        gameV.getPlayground().getBrokenBoatA().setBorder(BorderFactory.createLineBorder(Color.darkGray));
        gameV.getPlayground().getBrokenBoatB().setBorder(BorderFactory.createLineBorder(Color.darkGray));
        gameV.getPlayground().getBrokenBoatC().setBorder(BorderFactory.createLineBorder(Color.darkGray));

        ChoosingBossView choosingBossView = new ChoosingBossView(this);

    }

    /**
     * 产生一个1-6之间的随机整数（模拟骰子的功能）。
     *
     * @return 1-6之间的随机整数
     */
    public int rollDice() {
        int num = dice_generator.nextInt(5) + 1;
        System.out.println(num);
        return num;
    }

    public int getBossPlayerId() {
        return bossPlayerId;
    }

    public Player getBossPlayer() {
        return this.players[this.bossPlayerId];
    }

    /**
     * 返回当前正在进行操作的玩家对象。
     *
     * @return 当前玩家对象
     */
    public Player getCurrentPlayer() {
        return this.players[this.currentPlayerId];
    }

    /**
     * 根据玩家的ID返回玩家对象。
     *
     * @param id 要寻找的玩家ID
     * @return 玩家对象
     */
    public Player getPlayerByID(int id) {
        return this.players[id];
    }

    public void showCurrentState() {
        for (Boat s : this.board.getBoats()) {
            String res;
            res = "The " + s.getCargo_name() + " boat (" + s.getCargo_value() + "): [ ";
            for (SeatOfShip pos : s.getPos_list()) {
                if (pos.getSailorID() == -1)
                    res += String.valueOf(pos.getPrice()) + "$ ";
                else
                    res += this.players[pos.getSailorID()].getName() + " ";
            }

            res += "].";
            res += "The boat is at: " + s.getPos_in_the_sea();
            System.out.println(res);
        }
    }

    /**
     * 在所有轮结束后，根据船是否到港以及船上海员的归属，为每位玩家分配收益。
     */
    public void calculateProfits() {
        // for boats that get to the harbor
        // share the money by selling the cargo
        int money_to_share = 0;
        for (Boat s : this.board.getBoats()) {
            if (s.getPos_in_the_sea() > SEA_LENGTH) {
                System.out.println("The boat " + s.getCargo_name() + " has arrived");
                if (s.getFilledPosNum() != 0) {
                    money_to_share = s.getCargo_value() / s.getFilledPosNum();
                }
                System.out.println("money_to_share: " + money_to_share);
                for (SeatOfShip pos : s.getPos_list()) {
                    if (pos.getSailorID() != -1)
                        this.players[pos.getSailorID()].receiveProfit(money_to_share);
                }
            } else
                System.out.println("The boat " + s.getCargo_name() + " has sank!");
        }

//        for (Player p : this.players)
//            this.gameV.updatePlayersView(p.getPid(), false);
    }

    /**
     * 在终端打印出谁是获胜玩家，即得分最高（账户余额最高）。
     */
    public void showWinner() {
        int winner_id = 0;
        int high_balance = -1;
        for (Player p : this.players) {
            int temp_account = 0;
            for (Stock stock : p.getStocksMap().keySet()) {
                temp_account += stock.getCurrentPrice() * p.getStocksMap().get(stock);
            }
            temp_account += p.getAccount_balance();
            if (p.getAccount_balance() > high_balance) {
                winner_id = p.getPid();
                high_balance = p.getAccount_balance();
            }
            System.out.println(p.getName() + " has " + p.getAccount_balance() + "$");
        }
        System.out.println("The winner is: " + this.players[winner_id].getName());
    }

    /**
     * 切换玩家
     */
    public void switchPlayer() {
        this.currentPlayerId = (this.currentPlayerId + 1) % this.players.length;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Boat[] getBoats() {
        return board.getBoats();
    }

    public boolean isChoosing() {
        return choosing;
    }

    public void setChoosing(boolean choosing) {
        this.choosing = choosing;
    }

    public int getcurrentPlayerId() {
        return currentPlayerId;
    }

    public void setcurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public boolean isGameIsOver() {
        return gameIsOver;
    }

    public void setGameIsOver(boolean gameIsOver) {
        this.gameIsOver = gameIsOver;
    }

    public int getCurrent_round() {
        return current_round;
    }

    public void setCurrent_round(int current_round) {
        this.current_round = current_round;
    }

    public GameView getGameV() {
        return gameV;
    }

    public void setGameV(GameView gameV) {
        this.gameV = gameV;
    }

    public int getbossPlayerId() {
        return bossPlayerId;
    }

    public void setbossPlayerId(int bossPlayerId) {
        this.bossPlayerId = bossPlayerId;
    }

    public StockMarket getStockMarket() {
        return stockMarket;
    }

    public Board getBoard() {
        return board;
    }

    public boolean ifGameOver() {
        for (Stock stock : stockMarket.getStocks()) {
            if (stock.ifReachLimit()) {
                return true;
            }
        }

        return false;
    }

    public boolean ifExistPirate() {
        return board.ifExistPirate();
    }

    public boolean ifExistIslandOfNavigator() {
        return board.ifExistIslandOfNavigator();
    }

    public int getGame_status() {
        return game_status;
    }

    public void setGame_status(int game_status) {
        this.game_status = game_status;
    }

    public int getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(int selectTime) {
        this.selectTime = selectTime;
    }

    public Cargo[] getCargos() {
        return cargos;
    }

    public void setCargos(Cargo[] cargos) {
        this.cargos = cargos;
    }
}
