package manila.view.advancedBoatMove;

import manila.controller.moveBoatInAdvance.BasicController;
import manila.controller.moveBoatInAdvance.DownController;
import manila.controller.moveBoatInAdvance.UpController;
import manila.model.main.Cargo;
import manila.view.main.BoatView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdvancedBoatMove extends JFrame {

    private JPanel contentPane;
    private BoatView[] boatViews;
    private Cargo[] cargos;

    /**
     * Create the frame.
     */
    public AdvancedBoatMove(BoatView[] boatViews, Cargo[] cargos) {
        this.cargos = cargos;
        this.boatViews = boatViews;
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 452, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JButton btnBoat = new JButton("boat1");
//        ImageIcon boatIcon1 = new ImageIcon("src/image/boat1.png");
//        boatIcon1.setImage(boatIcon1.getImage().getScaledInstance(36, 80, Image.SCALE_DEFAULT));
//        btnBoat.setIcon(boatIcon1);
        btnBoat.setBounds(42, 125, 36, 80);
        contentPane.add(btnBoat);

        JButton btnBoat_1 = new JButton("boat2");
//        ImageIcon boatIcon2 = new ImageIcon("src/image/boat2.png");
//        btnBoat_1.setIcon(boatIcon2);
        btnBoat_1.setBounds(135, 125, 36, 80);
        contentPane.add(btnBoat_1);

        JButton btnBoat_2 = new JButton("boat3");
//        ImageIcon boatIcon3 = new ImageIcon("src/image/boat3.png");
//        btnBoat_2.setIcon(boatIcon3);
        btnBoat_2.setBounds(227, 125, 36, 80);
        contentPane.add(btnBoat_2);

        JButton btnBoat_3 = new JButton("boat");

        btnBoat_3.setBounds(322, 125, 36, 80);
        contentPane.add(btnBoat_3);

        UpController upController1 = new UpController(btnBoat, 0);
        UpController upController2 = new UpController(btnBoat_1, 1);
        UpController upController3 = new UpController(btnBoat_2, 2);
        UpController upController4 = new UpController(btnBoat_3, 3);

        DownController downController1 = new DownController(btnBoat, 0);
        DownController downController2 = new DownController(btnBoat_1, 1);
        DownController downController3 = new DownController(btnBoat_2, 2);
        DownController downController4 = new DownController(btnBoat_3, 3);

        ImageIcon down = new ImageIcon("src/image/down.png");
        ImageIcon up = new ImageIcon("src/image/up.png");

        JButton btnAdd = new JButton("add");
        // 缩放图片
//        up.setImage(up.getImage().getScaledInstance(54, 36, Image.SCALE_DEFAULT));
//        btnAdd.setIcon(up);
        btnAdd.setBounds(35, 202, 54, 36);
        btnAdd.setFont(btnAdd.getFont().deriveFont(btnAdd.getFont().getSize() - 2f));
        contentPane.add(btnAdd);
        btnAdd.addActionListener(upController1);

        JButton btnBack = new JButton("back");
//        btnBack.setIcon(down);
        btnBack.setBounds(35, 236, 54, 36);
        btnBack.setFont(btnAdd.getFont().deriveFont(btnAdd.getFont().getSize() - 2f));
        contentPane.add(btnBack);
        btnBack.addActionListener(downController1);

        JButton btnAdd_1 = new JButton("add");
//        btnAdd_1.setIcon(up);
        btnAdd_1.setBounds(124, 202, 54, 36);
        btnAdd_1.setFont(btnAdd.getFont().deriveFont(btnAdd.getFont().getSize() - 2f));
        contentPane.add(btnAdd_1);
        btnAdd_1.addActionListener(upController2);

        JButton btnBack_1 = new JButton("back");
//        btnBack_1.setIcon(down);
        btnBack_1.setBounds(124, 236, 54, 36);
        btnBack_1.setFont(btnAdd.getFont().deriveFont(btnAdd.getFont().getSize() - 2f));
        contentPane.add(btnBack_1);
        btnBack_1.addActionListener(downController2);

        JButton btnAdd_2 = new JButton("add");
//        btnAdd_2.setIcon(up);
        btnAdd_2.setBounds(220, 202, 54, 36);
        btnAdd_2.setFont(btnAdd.getFont().deriveFont(btnAdd.getFont().getSize() - 2f));
        contentPane.add(btnAdd_2);
        btnAdd_2.addActionListener(upController3);

        JButton btnBack_2 = new JButton("back");
//        btnBack_2.setIcon(down);
        btnBack_2.setBounds(220, 236, 54, 36);
        btnBack_2.setFont(btnAdd.getFont().deriveFont(btnAdd.getFont().getSize() - 2f));
        contentPane.add(btnBack_2);
        btnBack_2.addActionListener(downController3);

        JButton btnAdd_3 = new JButton("add");
//        btnAdd_3.setIcon(up);
        btnAdd_3.setBounds(310, 202, 54, 36);
        btnAdd_3.setFont(btnAdd.getFont().deriveFont(btnAdd.getFont().getSize() - 2f));
        contentPane.add(btnAdd_3);
        btnAdd_3.addActionListener(upController4);

        JButton btnBack_3 = new JButton("back");
//        btnBack_3.setIcon(down);
        btnBack_3.setBounds(310, 236, 55, 36);
        btnBack_3.setFont(btnAdd.getFont().deriveFont(btnAdd.getFont().getSize() - 2f));
        contentPane.add(btnBack_3);
        btnBack_3.addActionListener(downController4);


        BasicController basicController = new BasicController(this);
        JButton button = new JButton("确认");
        button.setBounds(371, 236, 75, 35);
//        contentPane.add(button);
        button.addActionListener(basicController);
        button.setActionCommand("confirm");

        upController1.setRecord(basicController.getRecord());
        upController2.setRecord(basicController.getRecord());
        upController3.setRecord(basicController.getRecord());
        upController4.setRecord(basicController.getRecord());
        downController1.setRecord(basicController.getRecord());
        downController2.setRecord(basicController.getRecord());
        downController3.setRecord(basicController.getRecord());
        downController4.setRecord(basicController.getRecord());

        JPanel panel = new JPanel();
        panel.setBounds(20, 21, 400, 100);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentPane.add(panel);
    }

    public BoatView[] getBoatViews() {
        return boatViews;
    }

    public void setBoatViews(BoatView[] boatViews) {
        this.boatViews = boatViews;
    }

    public Cargo[] getCargos() {
        return cargos;
    }
}
