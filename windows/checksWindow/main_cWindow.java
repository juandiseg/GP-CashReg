package windows.checksWindow;

import java.awt.event.ActionListener;
import iLayouts.GridLayoutApplyer;
import util.abstractUpdater;

import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class main_cWindow extends abstractUpdater {

    private JButton button1 = new JButton("Check in");
    private JButton button2 = new JButton("Check out");
    private JButton backButton = new JButton("Back");

    public main_cWindow(abstractUpdater previousWindow) {
        super(previousWindow, new GridLayoutApplyer(theFrame, 3));
    }

    @Override
    public void addComponents() {
        theFrame.setTitle("Check-in/Check-out menu");
        theFrame.add(button1);
        theFrame.add(button2);
        theFrame.add(backButton);
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                check_cWindow tempWind = new check_cWindow(temp, button1.getText());
                tempWind.updateToThisMenu();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                check_cWindow tempWind = new check_cWindow(temp, button2.getText());
                tempWind.updateToThisMenu();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateToPreviousMenu();
            }
        });
    }

}
