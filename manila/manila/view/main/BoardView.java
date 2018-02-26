package manila.view.main;

import manila.controller.main.BoatController;
import manila.controller.main.DiceController;
import manila.controller.main.GameController;
import manila.controller.position.*;
import manila.model.main.Boat;
import manila.model.main.Game;
import manila.model.position.SeatOfShip;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

//TODO 静雯

/**
 * 游戏场景界面类
 */
public class BoardView extends JPanel {
    /**
     * 一段航程在界面上的长度（直线的间隔）
     */
    public static final int SEA_INTERVAL = 31;
    /**
     * 一条小船的宽度
     */
    public static final int BOAT_W = 50;
    /**
     * 一条小船的高度
     */
    public static final int BOAT_H = 140;
    /**
     * 游戏场景宽度
     */
    private static final int GROUND_W = 400;
    /**
     * 游戏场景高度
     */
    private static final int GROUND_H = 800;
    /**
     * 第一条大海线段的起点x坐标
     */
    private static final int SEA_START_X = 50;
    /**
     * 第一条大海线段的起点y坐标
     */
    private static final int SEA_START_Y = 200;
    /**
     * 大海线段的宽
     */
    private static final int SEA_W = 300;
    /**
     * 船之间的横向间隔
     */
    private static final int BOAT_DISTANCE = 50;
    /**
     * 最左边小船左上角x坐标
     */
    private static final int BOAT_START_X = 75;
    /**
     * 最左边小船右上角y坐标
     */
    private static final int BOAT_START_Y = 635;

    /**
     * 小船上位置的宽度
     */
    private static final int POS_W = 40;
    /**
     * 小船上位置的高度
     */
    private static final int POS_H = 20;
    /**
     * 小船上最上面位置左上角的x坐标
     */
    private static final int POS_START_X = 5;
    /**
     * 小船上最上面位置左上角的y坐标
     */
    private static final int POS_START_Y = 20;
    /**
     * 小船上位置间在y方向上的间隔
     */
    private static final int POS_INTERVAL = 10;
    PositionView[] positionViews;
    private Game game;
    private BoatView[] boatViews;
    private JButton[] ports; // A B C 三个停船的位置
    private JButton[] repair_positions; // A B C 三个修船的位置
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Nan Zhao
    private JPanel mainPanel;
    private JPanel channel;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JTextField textField15;
    private JTextField textField16;
    private JTextField textField17;
    private JTextField textField18;
    private JPanel wharf;
    private JButton pay_forWharfC;
    private JButton label3;
    private JButton pay_forWharfB;
    private JButton label4;
    private JButton pay_forWharfA;
    private JButton label5;
    private JPanel wharf_forBoats;
    private PositionView boatC;
    private PositionView boatB;
    private PositionView boatA;
    private JPanel pirate;
    private JLabel pirate1;
    private PositionView pay_forPirate;
    private JPanel cargoA;
    private JLabel button56;
    private JLabel button57;
    private JLabel button58;
    private JLabel button59;
    private JLabel button60;
    private JLabel button61;
    private JPanel cargoB;
    private JLabel button62;
    private JLabel button63;
    private JLabel button64;
    private JLabel button65;
    private JLabel button66;
    private JLabel button67;
    private JPanel cargoC;
    private JLabel button68;
    private JLabel button69;
    private JLabel button70;
    private JLabel button71;
    private JLabel button72;
    private JLabel button73;
    private JPanel cargoD;
    private JLabel button74;
    private JLabel button75;
    private JLabel button76;
    private JLabel button77;
    private JLabel button78;
    private JLabel button79;
    private JButton cargo1;
    private PositionView position_A1;
    private PositionView position_A2;
    private PositionView position_A3;
    private PositionView position_A4;
    private JButton cargo2;
    private PositionView position_B1;
    private PositionView position_B2;
    private PositionView position_B3;
    private JButton cargo3;
    private PositionView position_C1;
    private PositionView position_C2;
    private PositionView position_c3;
    private JPanel repair_Boats;
    private PositionView brokenBoatC;
    private PositionView brokenBoarB;
    private PositionView brokenBaotA;
    private JPanel repair;
    private JButton pay_forRepair1;
    private JButton label6;
    private JButton pay_forRepair2;
    private JButton label7;
    private JButton pay_forRepair3;
    private JButton label8;
    private JPanel sailorA;
    private JLabel label2;
    private PositionView pay_forSailorA;
    private JPanel sailorB;
    private JLabel label1;
    private PositionView pay_forSailorB;
    private JPanel insurance;
    private JLabel label9;
    private PositionView pay_forInsurance;
    private JButton button1;

    public BoardView(Game g) {
        this.game = g;
//        this.setPreferredSize(new Dimension(GROUND_W, GROUND_H));
        this.addMouseListener(new GameController(this.game));


        boatViews = new BoatView[3];

        initComponents();
        setListener();
        initPositionViews();

//        this.initBoats();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Nan Zhao
        mainPanel = new JPanel();
        channel = new JPanel() {
            public void paint(Graphics g) {
                ImageIcon pic = new ImageIcon("src/image/channel1.png");
                Image img = pic.getImage();
                g.drawImage(img, 0, 0, 339, 408, pic.getImageObserver());
            }
        };
        textField6 = new JTextField();
        textField7 = new JTextField();
        textField8 = new JTextField();
        textField9 = new JTextField();
        textField10 = new JTextField();
        textField11 = new JTextField();
        textField12 = new JTextField();
        textField13 = new JTextField();
        textField14 = new JTextField();
        textField15 = new JTextField();
        textField16 = new JTextField();
        textField17 = new JTextField();
        textField18 = new JTextField();
        wharf = new JPanel();
        pay_forWharfC = new JButton();
        label3 = new JButton();
        ImageIcon pic1 = new ImageIcon("src/image/15.png");
        label3.setIcon(pic1);
        pay_forWharfB = new JButton();
        ImageIcon pic2 = new ImageIcon("src/image/8.png");
        label4 = new JButton();
        label4.setIcon(pic2);
        pay_forWharfA = new JButton();
        label5 = new JButton();
        ImageIcon pic3 = new ImageIcon("src/image/6.png");
        label5.setIcon(pic3);
        wharf_forBoats = new JPanel();
        boatC = new PositionView(game.getBoard().getPorts().get(2));
        boatB = new PositionView(game.getBoard().getPorts().get(1));
        boatA = new PositionView(game.getBoard().getPorts().get(0));
        pirate = new JPanel();
        pirate1 = new JLabel();
        pay_forPirate = new PositionView(game.getBoard().getPirateBays().get(0));

//        pirate =new JPanel(){
//            public void paint(Graphics g){
//                ImageIcon pic=new ImageIcon("src/image/pirate.png");
//                Image img=pic.getImage();
//                g.drawImage(img,0,0,53,60,pic.getImageObserver());
//            }
//        };
        pirate = new JPanel();

        cargoA = new JPanel() {
            public void paint(Graphics g) {
                ImageIcon pic = new ImageIcon("src/image/Coco.png");
                Image img = pic.getImage();
                g.drawImage(img, 0, 0, 60, 286, pic.getImageObserver());
            }
        };
        button56 = new JLabel();
        button57 = new JLabel();
        button58 = new JLabel();
        button59 = new JLabel();
        button60 = new JLabel();
        button61 = new JLabel();
        cargoB = new JPanel() {
            public void paint(Graphics g) {
                ImageIcon pic = new ImageIcon("src/image/nut.png");
                Image img = pic.getImage();
                g.drawImage(img, 0, 0, 60, 286, pic.getImageObserver());
            }
        };
        button62 = new JLabel();
        button63 = new JLabel();
        button64 = new JLabel();
        button65 = new JLabel();
        button66 = new JLabel();
        button67 = new JLabel();
        cargoC = new JPanel() {
            public void paint(Graphics g) {
                ImageIcon pic = new ImageIcon("src/image/silk.png");
                Image img = pic.getImage();
                g.drawImage(img, 0, 0, 60, 286, pic.getImageObserver());
            }
        };
        button68 = new JLabel();
        button69 = new JLabel();
        button70 = new JLabel();
        button71 = new JLabel();
        button72 = new JLabel();
        button73 = new JLabel();
        cargoD = new JPanel() {
            public void paint(Graphics g) {
                ImageIcon pic = new ImageIcon("src/image/jade.png");
                Image img = pic.getImage();
                g.drawImage(img, 0, 0, 60, 286, pic.getImageObserver());
            }
        };
        button74 = new JLabel();
        button75 = new JLabel();
        button76 = new JLabel();
        button77 = new JLabel();
        button78 = new JLabel();
        button79 = new JLabel();


        boatViews[0] = new BoatView(game.getBoats()[0]);
        boatViews[1] = new BoatView(game.getBoats()[1]);
        boatViews[2] = new BoatView(game.getBoats()[2]);

        cargo1 = new JButton();
        PositionView[] position_A = new PositionView[4];

        position_A1 = new PositionView(game.getBoats()[0].getPos_list()[0]);
        position_A2 = new PositionView(game.getBoats()[0].getPos_list()[1]);
        position_A3 = new PositionView(game.getBoats()[0].getPos_list()[2]);
        position_A4 = new PositionView(game.getBoats()[0].getPos_list()[3]);

        position_A[0] = position_A1;
        position_A[1] = position_A2;
        position_A[2] = position_A3;
        position_A[3] = position_A4;
        boatViews[0].setSeatViews(position_A);


        cargo2 = new JButton();
        position_B1 = new PositionView(game.getBoats()[1].getPos_list()[0]);
        position_B2 = new PositionView(game.getBoats()[1].getPos_list()[1]);
        position_B3 = new PositionView(game.getBoats()[1].getPos_list()[2]);

        PositionView[] position_B = new PositionView[3];
        position_B[0] = position_B1;
        position_B[1] = position_B2;
        position_B[2] = position_B3;
        boatViews[1].setSeatViews(position_B);

        cargo3 = new JButton();
        position_C1 = new PositionView(game.getBoats()[2].getPos_list()[0]);
        position_C2 = new PositionView(game.getBoats()[2].getPos_list()[1]);
        position_c3 = new PositionView(game.getBoats()[2].getPos_list()[2]);

        PositionView[] position_C = new PositionView[3];
        position_C[0] = position_C1;
        position_C[1] = position_C2;
        position_C[2] = position_c3;
        boatViews[2].setSeatViews(position_C);

        repair_Boats = new JPanel();
        brokenBoatC = new PositionView(game.getBoard().getShipyards().get(2));
        brokenBoarB = new PositionView(game.getBoard().getShipyards().get(1));
        brokenBaotA = new PositionView(game.getBoard().getShipyards().get(0));
        repair = new JPanel();
        pay_forRepair1 = new JButton();
        label6 = new JButton();
        pay_forRepair2 = new JButton();
        label7 = new JButton();
        pay_forRepair3 = new JButton();
        label8 = new JButton();
        sailorA = new JPanel();
        label2 = new JLabel();
        pay_forSailorA = new PositionView(game.getBoard().getIslandOfNavigators().get(1));

        sailorB = new JPanel();
        label1 = new JLabel();
        pay_forSailorB = new PositionView(game.getBoard().getIslandOfNavigators().get(0));

//        insurance = new JPanel(){
//            public void paint(Graphics g){
//                ImageIcon pic=new ImageIcon("src/INSURANCE.png");
//                Image img=pic.getImage();
//                g.drawImage(img,0,0,195,80,pic.getImageObserver());
//            }
//        };
        insurance = new JPanel();
        label9 = new JLabel();
        pay_forInsurance = new PositionView(game.getBoard().getInsuranceCompany());
        button1 = new JButton();

        //======== mainPanel ========
        {
            mainPanel.setBackground(new Color(204, 204, 255));

            // JFormDesigner evaluation mark
            mainPanel.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                            "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                            javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                            java.awt.Color.red), mainPanel.getBorder()));
            mainPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    if ("border".equals(e.getPropertyName())) throw new RuntimeException();
                }
            });

            mainPanel.setLayout(null);


            //======== wharf ========
            {
                wharf.setBackground(Color.orange);
                wharf.setForeground(Color.black);
                wharf.setLayout(new GridLayout(6, 0));

                //---- pay_forWharfC ----
                ImageIcon price2 = new ImageIcon("src/image/2.png");
                pay_forWharfC.setIcon(price2);
                pay_forWharfC.setText("2");
                pay_forWharfC.setForeground(new Color(128, 162, 163));
                pay_forWharfC.setBackground(Color.orange);
                wharf.add(pay_forWharfC);

                //---- label3 ----
                ImageIcon price15 = new ImageIcon("src/image/15.png");
                label3.setIcon(price15);
                label3.setText("      15");
                label3.setForeground(Color.magenta);
                wharf.add(label3);

                //---- pay_forWharfB ----
                ImageIcon price3 = new ImageIcon("src/image/3.png");
                pay_forWharfB.setIcon(price3);
                pay_forWharfB.setText("3");
                pay_forWharfB.setForeground(new Color(128, 162, 163));
                pay_forWharfB.setBackground(Color.orange);
                wharf.add(pay_forWharfB);

                //---- label4 ----
                ImageIcon price8 = new ImageIcon("src/image/8.png");
                label4.setIcon(price8);
                label4.setText("       8");
                label4.setForeground(Color.magenta);
                wharf.add(label4);

                //---- pay_forWharfA ----
                ImageIcon price4 = new ImageIcon("src/image/4.png");
                pay_forWharfA.setIcon(price4);
                pay_forWharfA.setText("4");
                pay_forWharfA.setForeground(new Color(128, 162, 163));
                pay_forWharfA.setBackground(Color.orange);
                wharf.add(pay_forWharfA);

                //---- label5 ----
                ImageIcon price6 = new ImageIcon("src/image/6.png");
                label5.setIcon(price6);
                label5.setText("       6");
                label5.setForeground(Color.magenta);
                wharf.add(label5);
            }
            mainPanel.add(wharf);
            wharf.setBounds(0, 0, 65, 205);

            //======== wharf_forBoats ========
            {
                wharf_forBoats.setBackground(Color.orange);
                wharf_forBoats.setLayout(new GridLayout(3, 0));

                //---- boatC ----
//                ImageIcon boatCI = new ImageIcon("src/image/C.png");
                ImageIcon boatCI = new ImageIcon("src/image/boat32.png");
                boatC.setIcon(boatCI);
                boatC.setText("C");
                boatC.setBackground(new Color(128, 162, 163));
                wharf_forBoats.add(boatC);

                //---- boatB ----
//                ImageIcon boatBI = new ImageIcon("src/image/B.png");
                ImageIcon boatBI = new ImageIcon("src/image/boat22.png");
                boatB.setIcon(boatBI);
                boatB.setText("B");
                boatB.setBackground(new Color(128, 162, 163));
                wharf_forBoats.add(boatB);

                //---- boatA ----
//                ImageIcon boatAI = new ImageIcon("src/image/A.png");
                ImageIcon boatAI = new ImageIcon("src/image/boat12.png");
                boatA.setIcon(boatAI);
                boatA.setText("A");
                boatA.setBackground(new Color(128, 162, 163));
                wharf_forBoats.add(boatA);
            }
            mainPanel.add(wharf_forBoats);
            wharf_forBoats.setBounds(65, 0, 200, 205);

            //======== pirate ========
            {
                pirate.setBackground(new Color(128, 162, 163));
                pirate.setLayout(new GridLayout(1, 2));

                //---- pirate1 ----
                ImageIcon pirateIcon = new ImageIcon("src/image/pirate.png");
                pirate1.setIcon(pirateIcon);
                pirate1.setText(" Pirate");
                pirate.add(pirate1);

                //---- pay_forPirate ----
                ImageIcon price5 = new ImageIcon("src/image/5.png");
                pay_forPirate.setIcon(price5);
                pay_forPirate.setText("5");
                pay_forPirate.setBackground(new Color(128, 162, 163));
                pirate.add(pay_forPirate);
            }
            mainPanel.add(pirate);
            pirate.setBounds(0, 228, 107, 47);

            //======== cargoA ========
            {
                cargoA.setBackground(new Color(128, 162, 163));
                cargoA.setForeground(new Color(187, 187, 187, 0));
                cargoA.setLayout(new GridLayout(6, 0));

                //---- button56 ----
                button56.setText("30");
                button56.setBackground(new Color(128, 162, 163));
                cargoA.add(button56);

                //---- button57 ----
                button57.setText("20");
                button57.setBackground(new Color(128, 162, 163));
                cargoA.add(button57);

                //---- button58 ----
                button58.setText("10");
                button58.setBackground(new Color(128, 162, 163));
                cargoA.add(button58);

                //---- button59 ----
                button59.setText("5");
                button59.setBackground(new Color(128, 162, 163));
                cargoA.add(button59);

                //---- button60 ----
                button60.setText("0");
                button60.setBackground(new Color(128, 162, 163));
                cargoA.add(button60);

                //---- button61 ----
                button61.setText("A");
                button61.setBackground(new Color(128, 162, 163));
                cargoA.add(button61);
            }
            mainPanel.add(cargoA);
            cargoA.setBounds(450, 600, 60, 286);

            //======== cargoB ========
            {
                cargoB.setBackground(new Color(128, 162, 163));
                cargoB.setForeground(new Color(187, 187, 187, 0));
                cargoB.setLayout(new GridLayout(6, 0));

                //---- button62 ----
                button62.setText("30");
                button62.setBackground(new Color(128, 162, 163));
                cargoB.add(button62);

                //---- button63 ----
                button63.setText("20");
                button63.setBackground(new Color(128, 162, 163));
                cargoB.add(button63);

                //---- button64 ----
                button64.setText("10");
                button64.setBackground(new Color(128, 162, 163));
                cargoB.add(button64);

                //---- button65 ----
                button65.setText("5");
                button65.setBackground(new Color(128, 162, 163));
                cargoB.add(button65);

                //---- button66 ----
                button66.setText("0");
                button66.setBackground(new Color(128, 162, 163));
                cargoB.add(button66);

                //---- button67 ----
                button67.setText("B");
                button67.setBackground(new Color(128, 162, 163));
                cargoB.add(button67);
            }
            mainPanel.add(cargoB);
            cargoB.setBounds(510, 600, 60, 286);

            //======== cargoC ========
            {
                cargoC.setBackground(new Color(204, 0, 204));
                cargoC.setLayout(new GridLayout(6, 0));

                //---- button68 ----
                button68.setText("30");
                button68.setBackground(new Color(204, 0, 204));
                cargoC.add(button68);

                //---- button69 ----
                button69.setText("20");
                button69.setBackground(new Color(204, 0, 204));
                cargoC.add(button69);

                //---- button70 ----
                button70.setText("10");
                button70.setBackground(new Color(204, 0, 204));
                cargoC.add(button70);

                //---- button71 ----
                button71.setText("5");
                button71.setBackground(new Color(204, 0, 204));
                cargoC.add(button71);

                //---- button72 ----
                button72.setText("0");
                button72.setBackground(new Color(204, 0, 204));
                cargoC.add(button72);

                //---- button73 ----
                button73.setText("C");
                button73.setBackground(new Color(204, 0, 204));
                cargoC.add(button73);
            }
            mainPanel.add(cargoC);
            cargoC.setBounds(570, 600, 60, 286);

            //======== cargoD ========
            {
                cargoD.setBackground(new Color(128, 162, 163));
                cargoD.setLayout(new GridLayout(6, 0));

                //---- button74 ----
                button74.setText("30");
                button74.setBackground(new Color(128, 162, 163));
                cargoD.add(button74);

                //---- button75 ----
                button75.setText("20");
                button75.setBackground(new Color(128, 162, 163));
                cargoD.add(button75);

                //---- button76 ----
                button76.setText("10");
                button76.setBackground(new Color(128, 162, 163));
                cargoD.add(button76);

                //---- button77 ----
                button77.setText("5");
                button77.setBackground(new Color(128, 162, 163));
                cargoD.add(button77);

                //---- button78 ----
                button78.setText("0");
                button78.setBackground(new Color(204, 0, 204));
                cargoD.add(button78);

                //---- button79 ----
                button79.setText("D");
                button79.setBackground(new Color(204, 0, 204));
                cargoD.add(button79);
            }
            mainPanel.add(cargoD);
            cargoD.setBounds(630, 600, 60, 286);

            //======== boatViews[0] ========
            {
                boatViews[0].setBackground(new Color(255, 255, 51));
                boatViews[0].setLayout(new GridLayout(5, 0));

                //---- cargo1 ----
                cargo1.setText("丝绸");
                cargo1.setBackground(new Color(255, 255, 51));
                boatViews[0].add(cargo1);
                boatViews[0].setCargoView(cargo1);

                //---- position_A1 ----
                position_A1.setText("3");
                position_A1.setBackground(new Color(255, 255, 51));
                boatViews[0].add(position_A1);

                //---- position_A2 ----
                position_A2.setText("4");
                position_A2.setBackground(new Color(255, 255, 51));
                boatViews[0].add(position_A2);

                //---- position_A3 ----
                position_A3.setText("5");
                position_A3.setBackground(new Color(255, 255, 51));
                boatViews[0].add(position_A3);

                //---- position_A4 ----
                position_A4.setText("5");
                position_A4.setBackground(new Color(255, 255, 51));
                boatViews[0].add(position_A4);
            }
            mainPanel.add(boatViews[0]);
            boatViews[0].setBounds(110, 635, 113, 250);

            //======== boatViews[1] ========
            {
                boatViews[1].setBackground(new Color(255, 0, 102));
                boatViews[1].setLayout(new GridLayout(4, 0));

                //---- cargo2 ----
                cargo2.setText("可可");
                cargo2.setBackground(new Color(255, 0, 102));
                boatViews[1].add(cargo2);
                boatViews[1].setCargoView(cargo2);

                //---- position_B1 ----
                position_B1.setText("3");
                position_B1.setBackground(new Color(255, 0, 102));
                boatViews[1].add(position_B1);

                //---- position_B2 ----
                position_B2.setText("4");
                position_B2.setBackground(new Color(255, 0, 102));
                boatViews[1].add(position_B2);

                //---- position_B3 ----
                position_B3.setText("5");
                position_B3.setBackground(new Color(255, 0, 102));
                boatViews[1].add(position_B3);
            }
            mainPanel.add(boatViews[1]);
            boatViews[1].setBounds(223, 635, 113, 250);

            //======== boatViews[2] ========
            {
                boatViews[2].setBackground(new Color(51, 51, 255));
                boatViews[2].setLayout(new GridLayout(4, 0));

                //---- cargo3 ----
                cargo3.setText("玉器");
                cargo3.setBackground(new Color(51, 51, 255));
                boatViews[2].add(cargo3);
                boatViews[2].setCargoView(cargo3);

                //---- position_C1 ----
                position_C1.setText("1");
                position_C1.setBackground(new Color(51, 51, 255));
                boatViews[2].add(position_C1);

                //---- position_C2 ----
                position_C2.setText("2");
                position_C2.setBackground(new Color(51, 51, 255));
                boatViews[2].add(position_C2);

                //---- position_c3 ----
                position_c3.setText("3");
                position_c3.setBackground(new Color(51, 51, 255));
                boatViews[2].add(position_c3);
            }
            mainPanel.add(boatViews[2]);
            boatViews[2].setBounds(336, 635, 113, 250);

            //======== channel ========
            {
                channel.setBackground(Color.pink);
                channel.setForeground(Color.blue);
                channel.setLayout(new GridLayout(13, 0));

                //---- textField6 ----
                textField6.setText("13");
                textField6.setBackground(Color.pink);
                textField6.setForeground(Color.blue);
                channel.add(textField6);

                //---- textField7 ----
                textField7.setText("12");
                textField7.setBackground(Color.pink);
                textField7.setForeground(Color.blue);
                channel.add(textField7);

                //---- textField8 ----
                textField8.setText("11");
                textField8.setBackground(Color.pink);
                textField8.setForeground(Color.blue);
                channel.add(textField8);

                //---- textField9 ----
                textField9.setText("10");
                textField9.setBackground(Color.pink);
                textField9.setForeground(Color.blue);
                channel.add(textField9);

                //---- textField10 ----
                textField10.setText("9");
                textField10.setBackground(Color.pink);
                textField10.setForeground(Color.blue);
                channel.add(textField10);

                //---- textField11 ----
                textField11.setText("8");
                textField11.setBackground(Color.pink);
                textField11.setForeground(Color.blue);
                channel.add(textField11);

                //---- textField12 ----
                textField12.setText("7");
                textField12.setBackground(Color.pink);
                textField12.setForeground(Color.blue);
                channel.add(textField12);

                //---- textField13 ----
                textField13.setText("6");
                textField13.setBackground(Color.pink);
                textField13.setForeground(Color.blue);
                channel.add(textField13);

                //---- textField14 ----
                textField14.setText("5");
                textField14.setBackground(Color.pink);
                textField14.setForeground(Color.blue);
                channel.add(textField14);

                //---- textField15 ----
                textField15.setText("4");
                textField15.setBackground(Color.pink);
                textField15.setForeground(Color.blue);
                channel.add(textField15);

                //---- textField16 ----
                textField16.setText("3");
                textField16.setBackground(Color.pink);
                textField16.setForeground(Color.blue);
                channel.add(textField16);

                //---- textField17 ----
                textField17.setText("2");
                textField17.setBackground(Color.pink);
                textField17.setForeground(Color.blue);
                channel.add(textField17);

                //---- textField18 ----
                textField18.setText("1");
                textField18.setBackground(Color.pink);
                textField18.setForeground(Color.blue);
                channel.add(textField18);
            }
            mainPanel.add(channel);
            channel.setBounds(110, 228, 339, 409);

            //======== repair_Boats ========
            {
                repair_Boats.setBackground(Color.orange);
                repair_Boats.setLayout(new GridLayout(3, 0));

                //---- brokenBoatC ----
//                ImageIcon boatCI = new ImageIcon("src/image/C.png");
                ImageIcon boatCI = new ImageIcon("src/image/boat32.png");
                brokenBoatC.setIcon(boatCI);
                brokenBoatC.setText("C");
                brokenBoatC.setBackground(Color.orange);
                repair_Boats.add(brokenBoatC);

                //---- brokenBoarB ----
//                ImageIcon boatBI = new ImageIcon("src/image/B.png");
                ImageIcon boatBI = new ImageIcon("src/image/boat22.png");
                brokenBoarB.setIcon(boatBI);
                brokenBoarB.setText("B");
                brokenBoarB.setBackground(Color.orange);
                repair_Boats.add(brokenBoarB);

                //---- brokenBaotA ----
//                ImageIcon boatAI = new ImageIcon("src/image/A.png");
                ImageIcon boatAI = new ImageIcon("src/image/boat12.png");
                brokenBaotA.setIcon(boatAI);
                brokenBaotA.setText("A");
                brokenBaotA.setBackground(new Color(128, 162, 163));
                repair_Boats.add(brokenBaotA);
            }
            mainPanel.add(repair_Boats);
            repair_Boats.setBounds(425, 0, 200, 205);

            //======== repair ========
            {
                repair.setBackground(Color.orange);
                repair.setForeground(new Color(204, 0, 204));
                repair.setLayout(new GridLayout(6, 0));

                //---- pay_forRepair1 ----
                ImageIcon price2 = new ImageIcon("src/image/2.png");
                pay_forRepair1.setIcon(price2);
                pay_forRepair1.setText("2");
                pay_forRepair1.setForeground(new Color(204, 0, 204));
                pay_forRepair1.setBackground(new Color(128, 162, 163));
                repair.add(pay_forRepair1);

                //---- label6 ----
                ImageIcon price15 = new ImageIcon("src/image/15.png");
                label6.setIcon(price15);
                label6.setText("      15");
                label6.setForeground(Color.magenta);
                repair.add(label6);

                //---- pay_forRepair2 ----
                ImageIcon price3 = new ImageIcon("src/image/3.png");
                pay_forRepair2.setIcon(price3);
                pay_forRepair2.setText("3");
                pay_forRepair2.setForeground(new Color(204, 0, 204));
                pay_forRepair2.setBackground(new Color(128, 162, 163));
                repair.add(pay_forRepair2);

                //---- label7 ----
                ImageIcon price8 = new ImageIcon("src/image/8.png");
                label7.setIcon(price8);
                label7.setText("       8");
                label7.setForeground(Color.magenta);
                repair.add(label7);

                //---- pay_forRepair3 ----
                ImageIcon price4 = new ImageIcon("src/image/4.png");
                pay_forRepair3.setIcon(price4);
                pay_forRepair3.setText("4");
                pay_forRepair3.setForeground(new Color(204, 0, 204));
                pay_forRepair3.setBackground(new Color(128, 162, 163));
                repair.add(pay_forRepair3);

                //---- label8 ----
                ImageIcon price6 = new ImageIcon("src/image/6.png");
                label8.setIcon(price6);
                label8.setText("       6");
                label8.setForeground(new Color(128, 162, 163));
                repair.add(label8);
            }
            mainPanel.add(repair);
            repair.setBounds(625, 0, 65, 205);

            //======== sailorA ========
            {
                sailorA.setBackground(Color.cyan);
                sailorA.setLayout(new GridLayout());

                //---- label2 ----
                ImageIcon sailor1 = new ImageIcon("src/image/sailor1.png");
                label2.setIcon(sailor1);
                label2.setText("SailorA");
                sailorA.add(label2);

                //---- pay_forSailorA ----
                ImageIcon price2 = new ImageIcon("src/image/2.png");
                pay_forSailorA.setIcon(price2);
                pay_forSailorA.setText("2");
                pay_forSailorA.setBackground(new Color(128, 162, 163));
                sailorA.add(pay_forSailorA);
            }
            mainPanel.add(sailorA);
            sailorA.setBounds(0, 500, 105, 45);

            //======== sailorB ========
            {
                sailorB.setBackground(Color.cyan);
                sailorB.setLayout(new GridLayout());

                //---- label1 ----
                ImageIcon sailor2 = new ImageIcon("src/image/sailor2.png");
                label1.setIcon(sailor2);
                label1.setText("SailorB");
                sailorB.add(label1);

                //---- pay_forSailorB ----
                ImageIcon price5 = new ImageIcon("src/image/5.png");
                pay_forSailorB.setIcon(price5);
                pay_forSailorB.setText("5");
                pay_forSailorB.setBackground(new Color(128, 162, 163));
                sailorB.add(pay_forSailorB);
            }
            mainPanel.add(sailorB);
            sailorB.setBounds(0, 455, 105, 45);

            //======== insurance ========
            {
                insurance.setBackground(new Color(128, 162, 163));
                insurance.setLayout(new GridLayout(1, 2));

                //---- label9 ----
                label9.setText("   INSURANCE");
                insurance.add(label9);

                //---- pay_forInsurance ----
                pay_forInsurance.setText("10");
                pay_forInsurance.setBackground(new Color(128, 162, 163));
                insurance.add(pay_forInsurance);
            }
            mainPanel.add(insurance);
            insurance.setBounds(495, 340, 195, 80);

            //---- button1 ----
            button1.setText("\u524d\u8fdb");
            mainPanel.add(button1);
            button1.setBounds(495, 525, 85, 48);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < mainPanel.getComponentCount(); i++) {
                    Rectangle bounds = mainPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = mainPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                mainPanel.setMinimumSize(preferredSize);
                mainPanel.setPreferredSize(preferredSize);
            }
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        this.add(mainPanel);
    }

    private void setListener() {

        // 前进按钮
        DiceController diceController = new DiceController(game);
        button1.addActionListener(diceController);


        // 船
        BoatController boatController1 = new BoatController(boatViews[0]);
        position_A1.addMouseListener(boatController1);
        position_A2.addMouseListener(boatController1);
        position_A3.addMouseListener(boatController1);
        position_A4.addMouseListener(boatController1);
        position_A1.addMouseMotionListener(boatController1);
        position_A2.addMouseMotionListener(boatController1);
        position_A3.addMouseMotionListener(boatController1);
        position_A4.addMouseMotionListener(boatController1);
        BoatController boatController2 = new BoatController(boatViews[1]);
        position_B1.addMouseListener(boatController2);
        position_B2.addMouseListener(boatController2);
        position_B3.addMouseListener(boatController2);
        position_B1.addMouseMotionListener(boatController2);
        position_B2.addMouseMotionListener(boatController2);
        position_B3.addMouseMotionListener(boatController2);
        BoatController boatController3 = new BoatController(boatViews[2]);
        position_C1.addMouseListener(boatController3);
        position_C2.addMouseListener(boatController3);
        position_c3.addMouseListener(boatController3);
        position_C1.addMouseMotionListener(boatController3);
        position_C2.addMouseMotionListener(boatController3);
        position_c3.addMouseMotionListener(boatController3);


        // 船上的座位
        SeatOfShipController[] seatOfShipControllers = new SeatOfShipController[10];

        seatOfShipControllers[0] = new SeatOfShipController(position_A1);
        seatOfShipControllers[1] = new SeatOfShipController(position_A2);
        seatOfShipControllers[2] = new SeatOfShipController(position_A3);
        seatOfShipControllers[3] = new SeatOfShipController(position_A4);
        seatOfShipControllers[4] = new SeatOfShipController(position_B1);
        seatOfShipControllers[5] = new SeatOfShipController(position_B2);
        seatOfShipControllers[6] = new SeatOfShipController(position_B3);
        seatOfShipControllers[7] = new SeatOfShipController(position_C1);
        seatOfShipControllers[8] = new SeatOfShipController(position_C2);
        seatOfShipControllers[9] = new SeatOfShipController(position_c3);
        for (int i = 0; i < 10; i++) {
            seatOfShipControllers[i].setGame(game);
        }

        position_A1.addActionListener(seatOfShipControllers[0]);
        position_A2.addActionListener(seatOfShipControllers[1]);
        position_A3.addActionListener(seatOfShipControllers[2]);
        position_A4.addActionListener(seatOfShipControllers[3]);
        position_B1.addActionListener(seatOfShipControllers[4]);
        position_B2.addActionListener(seatOfShipControllers[5]);
        position_B3.addActionListener(seatOfShipControllers[6]);
        position_C1.addActionListener(seatOfShipControllers[7]);
        position_C2.addActionListener(seatOfShipControllers[8]);
        position_c3.addActionListener(seatOfShipControllers[9]);

        // 领航员岛
        IslandOfNavigatorController[] islandOfNavigatorControllers = new IslandOfNavigatorController[2];
        islandOfNavigatorControllers[0] = new IslandOfNavigatorController(pay_forSailorA);
        islandOfNavigatorControllers[1] = new IslandOfNavigatorController(pay_forSailorB);
        pay_forSailorA.addActionListener(islandOfNavigatorControllers[0]);
        pay_forSailorB.addActionListener(islandOfNavigatorControllers[1]);
        for (int i = 0; i < 2; i++) {
            islandOfNavigatorControllers[i].setGame(game);
        }

        // 海盗湾
        PirateBayController pirateBayController = new PirateBayController(pay_forPirate);
        pay_forPirate.addActionListener(pirateBayController);
        pirateBayController.setGame(game);

        // 港口
        PortController[] portControllers = new PortController[3];
        portControllers[0] = new PortController(boatA);
        portControllers[1] = new PortController(boatB);
        portControllers[2] = new PortController(boatC);

        boatA.addActionListener(portControllers[0]);
        boatB.addActionListener(portControllers[1]);
        boatC.addActionListener(portControllers[2]);
        for (int i = 0; i < 3; i++) {
            portControllers[i].setGame(game);
        }

        // 修船厂
        ShipyardController[] shipyardControllers = new ShipyardController[3];
        shipyardControllers[0] = new ShipyardController(brokenBaotA);
        shipyardControllers[1] = new ShipyardController(brokenBoarB);
        shipyardControllers[2] = new ShipyardController(brokenBoatC);

        brokenBaotA.addActionListener(shipyardControllers[0]);
        brokenBoarB.addActionListener(shipyardControllers[1]);
        brokenBoatC.addActionListener(shipyardControllers[2]);
        for (int i = 0; i < 3; i++) {
            shipyardControllers[i].setGame(game);
        }

        // 保险公司
        InsuranceCompanyController insuranceCompanyController = new InsuranceCompanyController(pay_forInsurance);
        pay_forInsurance.addActionListener(insuranceCompanyController);
        insuranceCompanyController.setGame(game);


    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    /**
     * 对小船们的位置进行初始化
     */
    private void initBoats() {
        Boat[] boats = this.game.getBoats();
        for (int i = 0; i < boats.length; i++) {
            boats[i].setPosX(BOAT_START_X + i * (BOAT_W + BOAT_DISTANCE));
            boats[i].setPosY(BOAT_START_Y);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void moveBoats() {
        for (int i = 0; i < boatViews.length; i++) {
            int x = boatViews[i].getX();
            int y = BOAT_START_Y - boatViews[i].getBoat().getPos_in_the_sea() * 31;
            int width = boatViews[i].getWidth();
            int height = boatViews[i].getHeight();

            boatViews[i].setBounds(x, y, width, height);

        }


    }

    public void initPositionViews() {
        positionViews = new PositionView[10];
        positionViews[0] = pay_forSailorA;
        positionViews[1] = pay_forSailorB;
        positionViews[2] = pay_forPirate;
        positionViews[3] = boatA;
        positionViews[4] = boatB;
        positionViews[5] = boatC;
        positionViews[6] = brokenBaotA;
        positionViews[7] = brokenBoarB;
        positionViews[8] = brokenBoatC;
        positionViews[9] = pay_forInsurance;
    }

    public PositionView[] getPositionViews() {
        return positionViews;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

//        this.drawSea(g2);
//        this.drawBoats(g2);
    }

    /**
     * 画出大海（一组线段）
     *
     * @param g2 图形类
     */
    public void drawSea(Graphics2D g2) {
        for (int i = 0; i <= Game.SEA_LENGTH; i++) {
            g2.draw(new Line2D.Double(SEA_START_X, SEA_START_Y + i * SEA_INTERVAL,
                    SEA_START_X + SEA_W, SEA_START_Y + i * SEA_INTERVAL));
            g2.drawString(Game.SEA_LENGTH - i + "", SEA_START_X + SEA_W, SEA_START_Y + i * SEA_INTERVAL);
        }
    }

    /**
     * 根据小船的信息在界面上画出一条小船以及船上的所有位置
     *
     * @param g2 图形类
     * @param b  一个小船对象
     */
    public void drawBoat(Graphics2D g2, Boat b) {
        g2.setColor(Color.GRAY);
        g2.fill(new Rectangle2D.Double(b.getPosX(), b.getPosY(), BOAT_W, BOAT_H));

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
        g2.drawString(b.getCargo_value() + "", b.getPosX() + 14, b.getPosY() + 18);

        SeatOfShip[] pos_list = b.getPos_list();
        for (int i = 0; i < pos_list.length; i++) {
            if (pos_list[i].getSailorID() == -1) {
                g2.setColor(Color.WHITE);
                Rectangle2D r_pos = new Rectangle2D.Double(b.getPosX() + POS_START_X,
                        b.getPosY() + POS_START_Y + i * (POS_H + POS_INTERVAL),
                        POS_W, POS_H);
                g2.fill(r_pos);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
                g2.drawString(pos_list[i].getPrice() + "", (int) r_pos.getX() + POS_W / 2 - 4, (int) r_pos.getY() + POS_H / 2 + 5);
            } else {
                g2.setColor(this.game.getPlayerByID(pos_list[i].getSailorID()).getC());
                g2.fill(new Rectangle2D.Double(b.getPosX() + POS_START_X,
                        b.getPosY() + POS_START_Y + i * (POS_H + POS_INTERVAL),
                        POS_W, POS_H));
            }
        }
    }

    /**
     * 画出所有的小船
     *
     * @param g2 图形类
     */
    public void drawBoats(Graphics2D g2) {
        Boat[] boats = this.game.getBoats();
        for (int i = 0; i < boats.length; i++) {
            this.drawBoat(g2, boats[i]);
        }
    }

    public BoatView[] getBoatViews() {
        return boatViews;
    }

    public JButton[] getPorts() {
        return ports;
    }

    public void setPorts(JButton[] ports) {
        this.ports = ports;
    }

    public JButton[] getRepair_positions() {
        return repair_positions;
    }

    public void setRepair_positions(JButton[] repair_positions) {
        this.repair_positions = repair_positions;
    }


    //*****************************新加*************************************


    public PositionView getPay_forPirate() {
        return pay_forPirate;
    }

    public PositionView getPay_forWharfA() {
        return boatA;
    }

    public PositionView getPay_forWharfB() {
        return boatB;
    }

    public PositionView getPay_forWharfC() {
        return boatC;
    }

    public PositionView getPay_forRepair1() {
        return brokenBaotA;
    }

    public PositionView getPay_forRepair2() {
        return brokenBoarB;
    }

    public PositionView getPay_forRepair3() {
        return brokenBoatC;
    }

    public PositionView getPay_forSailorA() {
        return pay_forSailorA;
    }

    public PositionView getPay_forSailorB() {
        return pay_forSailorB;
    }

    public PositionView getPay_forInsurance() {
        return pay_forInsurance;
    }

    public JButton getBoatA() {
        return boatA;
    }

    public JButton getBoatB() {
        return boatB;
    }

    public JButton getBoatC() {
        return boatC;
    }

    public JButton getBrokenBoatA() {
        return brokenBaotA;
    }

    public JButton getBrokenBoatB() {
        return brokenBoarB;
    }

    public JButton getBrokenBoatC() {
        return brokenBoatC;
    }


}
