package manila.controller.event;

import manila.view.eventView.PirateOnBoatView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PirateOnBoat implements ActionListener {

    private PirateOnBoatView pirateOnBoatView;

    public PirateOnBoat(PirateOnBoatView pirateOnBoatView) {
        this.pirateOnBoatView = pirateOnBoatView;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        pirateOnBoatView.dispose();
    }
}
