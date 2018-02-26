package manila.controller;

import manila.view.BeginView;
import manila.view.choosingBoss.ChoosingBossView;
import manila.view.main.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BeginController implements ActionListener {
    private BeginView beginView;

    public BeginController(BeginView beginView) {
        this.beginView = beginView;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("begin")) {
            // TODO Auto-generated method stub
            JFrame mw = new JFrame();
            // 设置标题
            mw.setTitle("Manila");
            GameView gv = new GameView();
            // 设置关闭窗口时停止程序
            mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mw.setContentPane(gv);
            mw.pack();
            mw.setVisible(true);

            ChoosingBossView cbv = new ChoosingBossView(gv.getGame());
        }

        beginView.dispose();
//        beginView.setVisible(false);
    }

}
