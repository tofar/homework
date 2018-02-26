package manila.view.eventView;

import manila.controller.event.PirateOnBoat;

import javax.swing.*;

public class PirateOnBoatView extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public PirateOnBoatView() {

        setBounds(100, 100, 403, 300);
        contentPane = new PiratePanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(80, 6, 237, 223);
        contentPane.add(panel);

        JButton button = new JButton("确认");
        button.setBounds(159, 230, 75, 42);
        contentPane.add(button);

        PirateOnBoat pirateOnBoat = new PirateOnBoat(this);
        button.addActionListener(pirateOnBoat);
    }

}
