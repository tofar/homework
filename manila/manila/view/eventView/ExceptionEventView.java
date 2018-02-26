package manila.view.eventView;

import javax.swing.*;
import java.awt.*;

public class ExceptionEventView extends JFrame {
    JLabel lblEventlabel;
    JPanel panel;
    JLabel panel_1;
    private int ID;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public ExceptionEventView(int ID) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 260);
        contentPane = new EventPanel(ID);
        setContentPane(contentPane);
        contentPane.setLayout(null);
        switch (ID) {
            case 1:
                lblEventlabel = new JLabel("沉船湾的宝藏！");
                break;
            case 2:
                lblEventlabel = new JLabel("苏比克湾大海啸！");
                break;
            case 3:
                lblEventlabel = new JLabel("蔓延的坏血病");
                break;
            case 4:
                lblEventlabel = new JLabel("菲律宾金融危机");
                break;
            case 5:
                lblEventlabel = new JLabel("海岛黑市");
                break;
            case 6:
                lblEventlabel = new JLabel("皇家海军巡航");
                break;
            default:
                lblEventlabel = new JLabel("皇家海军巡航");
                break;
        }
        lblEventlabel.setBounds(149, 6, 133, 23);
        contentPane.add(lblEventlabel);


        panel_1 = new JLabel();
        switch (ID) {
            case 1:
                panel_1 = new JLabel("发现了传说中的沉船湾！\n       获得20披索！");
                break;
            case 2:
                panel_1 = new JLabel("菲律宾南部的苏比克湾发生了大海啸！ \n     本轮所有船只退回原点！");
                break;
            case 3:
                panel_1 = new JLabel("恐怖的坏血病在船上蔓延！ \n   移除最后一名海员！");
                break;
            case 4:
                panel_1 = new JLabel("黄金价格暴跌！菲律宾爆发了金融危机！  \n  随机两张股票价值下跌！");
                break;
            case 5:
                panel_1 = new JLabel("发现了神秘群岛上的黑市交易！ \n  立即获得一张股票！");
                break;
            case 6:
                panel_1 = new JLabel("菲律宾海的海贼们被苏禄皇家海军一网打尽！ \n 移除本轮所有海盗！");
                break;
            default:
                panel_1 = new JLabel("菲律宾海的海贼们被苏禄皇家海军一网打尽！ \\n 移除本轮所有海盗！");
                break;
        }
        panel_1.setForeground(Color.WHITE);
        panel_1.setBounds(80, 156, 133, 23);
        contentPane.add(panel_1);
    }
}
