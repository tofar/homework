package manila.view;

import manila.controller.BeginController;

import javax.swing.*;

public class BeginView extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public BeginView() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 719);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        this.setTitle("manila");


        BeginController beginController = new BeginController(this);

        JButton btnBegin = new JButton("开始游戏");
        btnBegin.setBounds(755, 100, 130, 60);
        contentPane.add(btnBegin);
        btnBegin.addActionListener(beginController);
        btnBegin.setActionCommand("begin");

        JButton btnGameOver = new JButton("退出游戏");
        btnGameOver.setBounds(755, 200, 130, 60);
        contentPane.add(btnGameOver);
        btnGameOver.addActionListener(beginController);
        btnGameOver.setActionCommand("out");
    }


}
