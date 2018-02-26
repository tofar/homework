package manila.controller.main;

import manila.view.main.BoatView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class BoatController implements MouseMotionListener, MouseListener {

    /**
     * 船的视图层
     */
    private BoatView boatView;
    /**
     * 拖动的时候第一次点击的坐标Y轴
     */
    private int entered_y;
    /**
     * 鼠标当前的y轴
     */
    private int currentY;
    /**
     * 船最初始的位置（0-13）
     */
    private int start_pos;
    /**
     * 最开始的船的view的y坐标
     */
    private int boat_startY;

    public BoatController(BoatView boatView) {
        this.boatView = boatView;

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

        if (boatView.getStatus() == 0) {
            boat_startY = boatView.getY();
            System.out.println("your mouse dragged.");
            entered_y = mouseEvent.getY();
            boatView.setStatus(1);
            start_pos = boatView.getBoat().getPos_in_the_sea();
        } else {
            if (boatView.getStatus() == 1) {
                currentY = mouseEvent.getY();
                if (entered_y - currentY < 62 * 3 && entered_y - currentY > 0) {
                    boatView.setBounds(boatView.getX(), boat_startY - (entered_y - currentY) / 3, boatView.getWidth(), boatView.getHeight());
                }

            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {


    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (boatView.getStatus() != -1) {
            System.out.println("your mouse clicked.");
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {


    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        System.out.println("Your mouse released");

        if (boatView.getStatus() == 1) {
            currentY = mouseEvent.getY();
            if ((entered_y - currentY) > 62 * 3) {
                boatView.getBoat().setPos_in_the_sea(start_pos + 2);
            } else {
                if ((entered_y - currentY) > 0) {
                    int n = (entered_y - currentY) / (3 * 31) + 1;
                    boatView.getBoat().setPos_in_the_sea(start_pos + n);
                } else {
                    boatView.getBoat().setPos_in_the_sea(start_pos);
                }
            }

            moveBoatView();

            boatView.setStatus(-1);
        }
    }

    public void moveBoatView() {
        int x = boatView.getX();
        int y = 635 - boatView.getBoat().getPos_in_the_sea() * 31;
        int width = boatView.getWidth();
        int height = boatView.getHeight();

        boatView.setBounds(x, y, width, height);
    }

}
