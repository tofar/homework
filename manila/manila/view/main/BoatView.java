package manila.view.main;

import manila.model.main.Boat;

import javax.swing.*;

// TODO 静雯

/**
 * 船的view
 */
public class BoatView extends JPanel {

    private Boat boat;
    private JButton cargoView;
    private PositionView[] seatViews;

    /**
     * 小船的JPanel 是否在领航员的时间段，-1 则不在，0则还没点击，1则在拖动期间
     */
    private int status;

    /**
     * 小船的View层构造函数
     *
     * @param boat 小船
     */
    public BoatView(Boat boat) {
        this.boat = boat;
        status = -1;
    }

    public Boat getBoat() {
        return boat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JButton getCargoView() {
        return cargoView;
    }

    public void setCargoView(JButton cargoView) {
        this.cargoView = cargoView;
    }

    public PositionView[] getSeatViews() {
        return seatViews;
    }

    public void setSeatViews(PositionView[] seatViews) {
        this.seatViews = seatViews;
    }
}
