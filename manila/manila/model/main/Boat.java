package manila.model.main;

import manila.model.position.SeatOfShip;
import manila.view.main.BoardView;

/**
 * 小船类，船上装有货物，船上有位置可以装海员。
 */
public class Boat {

    /**
     * 货物
     */
    private Cargo cargo;
    /**
     * 船上的空位数组
     */
    private SeatOfShip[] pos_list;
    /**
     * 船在海中的位置
     */
    private int pos_in_the_sea;

    private boolean is_arrived;

    /**
     * 船（左上角）在图形界面上的x坐标
     */
    private int posX;
    /**
     * 船（左上角）在图形界面上的y坐标
     */
    private int posY;

    /**
     * 小船构造函数
     *
     * @param cargo 货物
     * @param pl    一组位置
     */
    public Boat(Cargo cargo, SeatOfShip[] pl) {
        this.cargo = cargo;
        this.pos_list = pl;
        this.pos_in_the_sea = 0;
        this.is_arrived = false;
    }

    /**
     * 当一个玩家分配海员登上该船时，调用该函数用以更新船上位置的信息
     *
     * @param pid 登船玩家的ID
     */
    public void getOnboard(int pid) {
        this.pos_list[getAvailPosIndex()].setSailorID(pid);
    }

    /**
     * 使船在海中前进，更新船在海中的位置和在船在图形界面上的位置
     *
     * @param step 船在海中前进的步数
     */
    public void move(int step) {
        this.pos_in_the_sea += step;
        this.posY -= step * BoardView.SEA_INTERVAL;
    }

    /**
     * 获得该船当前空着的位置的编号（登船时自动从较低的编号开始）
     *
     * @return 当前编号最小的空位所对应的编号值
     */
    public int getAvailPosIndex() {
        for (int i = 0; i < this.pos_list.length; i++) {
            if (this.pos_list[i].getPlayer() == null)
                return i;
        }
        // no position left
        return -1;
    }

    /**
     * 返回当前船上已有多少个坐了人的位置数
     *
     * @return 已有人的位置数
     */
    public int getFilledPosNum() {
        int pos_ind = getAvailPosIndex();
        if (pos_ind == -1)
            return this.pos_list.length;
        else
            return pos_ind;
    }

    /**
     * 返回当前编号最小的空位对应的登船费用
     *
     * @return 当前编号最小的空位对应的登船费用
     */
    public int getAvailPosPrice() {
        for (int i = 0; i < this.pos_list.length; i++) {
            if (this.pos_list[i].getSailorID() == -1)
                return this.pos_list[i].getPrice();
        }
        return -1;
    }

    /**
     * 判断鼠标光标是否在该船的范围内
     *
     * @param x 光标的横坐标
     * @param y 光标的纵坐标
     * @return 是否在该船的范围内
     */
    public boolean isCursorInside(int x, int y) {
        if (x > this.posX && x < this.posX + BoardView.BOAT_W
                && y > this.posY && y < this.posY + BoardView.BOAT_H)
            return true;
        return false;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getCargo_name() {
        return cargo.getCargoName();
    }

    public int getCargo_value() {
        return cargo.getProfit();
    }

    public int getPos_in_the_sea() {
        return pos_in_the_sea;
    }

    public void setPos_in_the_sea(int pos_in_the_sea) {
        this.pos_in_the_sea = pos_in_the_sea;
    }

    public SeatOfShip[] getPos_list() {
        return pos_list;
    }

    public void setPos_list(SeatOfShip[] pos_list) {
        this.pos_list = pos_list;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean getIs_arrived() {
        return is_arrived;
    }

    public void setIs_arrived(boolean is_arrived) {
        this.is_arrived = is_arrived;
    }
}
